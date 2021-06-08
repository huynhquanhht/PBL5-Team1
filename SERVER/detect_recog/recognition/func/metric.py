print("METRIC>FILE")
from torch.nn import Linear, Conv2d, BatchNorm1d, BatchNorm2d, PReLU, Sequential, Module, Parameter
import torch
import math
from detect_recog.recognition.func.utils import l2_norm


# Arcface
# reference[https://arxiv.org/abs/1801.05599]
# reference[https://arxiv.org/abs/1801.07698]
# reference[https://arxiv.org/abs/1704.06369]  
class Arcface(Module):
    def __init__(self, embedding_size=512, classnum=5124,  s=64., m=0.5):
        super(Arcface, self).__init__()
        self.classnum = classnum
        # weight
        self.weight = Parameter(torch.Tensor(embedding_size, classnum))
        # initial weight
        self.weight.data.uniform_(-1, 1).renorm_(2, 1, 1e-5).mul_(1e5)
        # Hang so trung phat m = 0.5 (tuong duong 60 do) reference[https://arxiv.org/abs/1801.07698]
        self.m = m
        # gia tri mac dinh cua tham so scalar= 64, reference[https://arxiv.org/abs/1801.07698]
        self.s = s
        self.cos_m = math.cos(m)
        self.sin_m = math.sin(m)
        # Khong che nam trong khong 0 den pi
        self.threshold = math.cos(math.pi - m)
        # Dieu kien cua cosface
        self.mm = self.sin_m * m

    def forward(self, embbedings, label):
        """Cong thuc cap nhat theta cua arcface: 
            cos(m + theta) = cos(m)cos(theta) - sin(m)sin(theta)
        Voi:
            0 <= m + theta <= Pi
        Neu (m + theta) >= Pi, thi theta >= Pi - m thuoc [0, Pi]
        Ta co:
            cos(theta) < cos(Pi - m)
        Nen co the dung cos(Pi - m) nhu threshold de check dieu kien cua
        (m + theta) co vuot qua [0, Pi] hay khong

        Args:
            embedding_size:  512
            s: scale 
            m: margin
        """
        # weights norm
        nB = len(embbedings)
        weight_norm = l2_norm(self.weight, axis=0)
        # cos(theta+m)
        cos_theta = torch.mm(embbedings, weight_norm)
        #  output = torch.mm(embbedings,kernel_norm)
        cos_theta = cos_theta.clamp(-1, 1)  # for numerical stability
        cos_theta_2 = torch.pow(cos_theta, 2)
        sin_theta_2 = 1 - cos_theta_2
        sin_theta = torch.sqrt(sin_theta_2)
        cos_theta_m = (cos_theta * self.cos_m - sin_theta * self.sin_m)
        # Dieu kien thuc hien arcface  theta+m thuoc [0, pi]
        #      0<=theta+m<=pi
        #     -m<=theta<=pi-m
        # Neu theta khong thuoc [0,pi], dung cosface de thay the
        keep_val = (cos_theta - self.mm)
        cos_theta_m[cos_theta - self.threshold <=
                    0] = keep_val[cos_theta - self.threshold <= 0]

        output = cos_theta * 1.0  # dua ve so thuc de lam viec
        idx_ = torch.arange(0, nB, dtype=torch.long)
        output[idx_, label] = cos_theta_m[idx_, label]
        # o day khong ap dung softmax theo nhu bai bao vi  khi su dung cross-entropy, no se tu dan dung softmax len dau vao
        # nen viec them soft max o day la khong nen
        # [https://discuss.pytorch.org/t/multi-class-cross-entropy-loss-and-softmax-in-pytorch/24920]
        output *= self.s  # scale de lam softmax hoat dong, tham khao them chi tiet trong normface

        return output

