from torch.nn import Linear, Conv2d, BatchNorm1d, BatchNorm2d, PReLU, Sequential, Module
from recognition.func.utils import l2_norm


class Flatten(Module):
    def forward(self, input):
        return input.view(input.size(0), -1)


class Conv_Bn(Module):
    def __init__(self, in_c, out_c, kernel=1, stride=1, padding=0, groups=1):
        super(Conv_Bn, self).__init__()
        self.conv = Conv2d(in_c, out_channels=out_c, kernel_size=kernel,groups=groups, stride=stride, padding=padding, bias=False)
        self.bn = BatchNorm2d(out_c)

    def forward(self, x):
        x = self.conv(x)
        x = self.bn(x)
        return x


class Conv_Bn_Prelu(Module):
    def __init__(self, in_c, out_c, kernel=1, stride=1, padding=0, groups=1):
        super(Conv_Bn_Prelu, self).__init__()
        self.conv = Conv2d(in_c, out_channels=out_c, kernel_size=kernel,groups=groups, stride=stride, padding=padding, bias=False)
        self.bn = BatchNorm2d(out_c)
        self.prelu = PReLU(out_c)

    def forward(self, x):
        x = self.conv(x)
        x = self.bn(x)
        x = self.prelu(x)
        return x


class DepthWise(Module):
    def __init__(self, in_c, out_c,  kernel=3, stride=2, padding=1, groups=1):
        super(DepthWise, self).__init__()
        self.conv = Conv_Bn_Prelu(in_c, out_c=groups, kernel=1, padding=0, stride=1)
        self.conv_bn_prelu = Conv_Bn_Prelu(groups, groups, groups=groups, kernel=kernel, padding=padding, stride=stride)
        self.conv_bn = Conv_Bn(groups, out_c, kernel=1, padding=0, stride=1)

    def forward(self, x):
        x = self.conv(x)
        x = self.conv_bn_prelu(x)
        x = self.conv_bn(x)
        return x


class DepthWise_Res(Module):
    def __init__(self, in_c, out_c,  kernel=3, stride=2, padding=1, groups=1):
        super(DepthWise_Res, self).__init__()
        self.conv = Conv_Bn_Prelu(in_c, out_c=groups, kernel=1, padding=0, stride=1)
        self.conv_bn_prelu = Conv_Bn_Prelu(groups, groups, groups=groups, kernel=kernel, padding=padding, stride=stride)
        self.conv_bn = Conv_Bn(groups, out_c, kernel=1, padding=0, stride=1)
      
    def forward(self, x):
        short_cut = x
        x = self.conv(x)
        x = self.conv_bn_prelu(x)
        x = self.conv_bn(x)
        # reference :[https://arxiv.org/abs/1801.04381.pdf]: Residual on mobilenetV2
        # khong can downsample(x), vi so luong chanel kha nho
        # Residual: giai thich tai [https://jarvislabs.ai/blogs/resnet]
        output = short_cut + x
        return output


class MultiDepthWise_Res(Module):
    def __init__(self, c, num_block, groups, kernel=3, stride=1, padding=1):
        super(MultiDepthWise_Res, self).__init__()
        modules = []
        for _ in range(num_block):
            modules.append(DepthWise_Res(c, c, kernel=kernel,padding=padding, stride=stride, groups=groups))
        self.model = Sequential(*modules)

    def forward(self, x):
        return self.model(x)


class MobileFaceNet(Module):
    def __init__(self, embedding_size):
        super(MobileFaceNet, self).__init__()
        self.conv1 = Conv_Bn_Prelu(3, 64, kernel=3, stride=2, padding=1)  
        self.conv2 = Conv_Bn_Prelu(64, 64, kernel=3, stride=1, padding=1, groups=64)  
        self.conv3 = DepthWise(64, 64, kernel=3, stride=2, padding=1, groups=128)  
        self.conv4 = MultiDepthWise_Res(64, kernel=3, stride=1, padding=1, num_block=4, groups=128)  
        self.conv5 = DepthWise(64, 128, kernel=3, stride=2, padding=1, groups=256)  
        self.conv6 = MultiDepthWise_Res(128,  kernel=3, stride=1, padding=1,num_block=6, groups=256)  
        self.conv7 = DepthWise(128, 128, kernel=3, stride=2, padding=1, groups=512)  
        self.conv8 = MultiDepthWise_Res(128,  kernel=3, stride=1, padding=1,num_block=2, groups=256)  
        self.conv9 = Conv_Bn_Prelu(128, 512, kernel=1, stride=1, padding=0)  
        self.conv10 = Conv_Bn(512, 512,  kernel=7, stride=1, padding=0,groups=512)  
        self.flatten = Flatten()
        self.linear = Linear(512, embedding_size, bias=False)
        self.bn = BatchNorm1d(embedding_size)

    def forward(self, x):
        out = self.conv1(x)
        out = self.conv2(out)
        out = self.conv3(out)
        out = self.conv4(out)
        out = self.conv5(out)
        out = self.conv6(out)
        out = self.conv7(out)
        out = self.conv8(out)
        out = self.conv9(out)
        out = self.conv10(out)
        out = self.flatten(out)
        out = self.linear(out)
        out = self.bn(out)
        return l2_norm(out)
