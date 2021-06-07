from recognition.data.extract_data import  get_train_loader, get_val_data
from datetime import datetime
from recognition.func.metric import Arcface
from recognition.func.mobileFaceNet import MobileFaceNet
from recognition.func.utils import get_time, l2_norm,  extract_bn_para

from torchvision import transforms as trans
from torch import optim
from tqdm import tqdm
from PIL import Image
import numpy as np
import torch

class learner(object):
    def __init__(self, conf, inference=False):
        self.model = MobileFaceNet(conf.embedding_size).to(conf.device)
        print('Gen MobileFaceNet')
        print()

        if not inference:
            self.e_to_update_lr = conf.e_to_update_lr
            self.loader, self.class_num = get_train_loader(conf)
            self.step = 0
            self.head = Arcface(embedding_size=conf.embedding_size,
                                classnum=self.class_num).to(conf.device)
            print('Gen Head Arcface')
            only_bn, wo_bn = extract_bn_para(self.model)
            # reference [https://arxiv.org/abs/1801.04381]
            self.optimizer = optim.SGD([
                {'params': wo_bn[:-1], 'weight_decay': 4e-5},
                {'params': [wo_bn[-1]] +
                 [self.head.weight], 'weight_decay': 4e-4},
                {'params': only_bn}
            ], lr=conf.lr, momentum=conf.momentum)

            print('Gen Optimizer')
            self.log_loss_per = len(self.loader)//100
            self.save_per = len(self.loader)//10
            print("len loader", len(self.loader))
            print("log_loss_per: ", self.log_loss_per)
            print("save_per: ".self.save_per)
            self.agedb_30, self.cfp_fp, self.lfw, self.agedb_30_issame, self.cfp_fp_issame, self.lfw_issame = get_val_data(self.loader.dataset.root.parent)
        else:
            self.threshold = conf.threshold

   

    def log_info_loss_train(self, loss, step, epoch, conf):
        path = str(conf.log_path/'logloss.txt')
        f = open(path, "a+")
        f.write('{} {} {}\n'.format(loss, epoch, step))
        f.close()

    def save_state(self, conf, to_save_folder=False, extra=None, model_only=False):
        if to_save_folder:
            save_path = conf.save_path
        else:
            save_path = conf.model_path
        save_time = get_time()
        torch.save(self.model.state_dict(), save_path /
                   ('model_{}_step:{}_{}.pth'.format(save_time,  self.step, extra)))

        if not model_only:
            torch.save(self.head.state_dict(), save_path / (
                'head_{}_step:{}_{}.pth'.format(save_time,  self.step, extra)))
            torch.save(self.optimizer.state_dict(), save_path / (
                'optim_{}_step:{}_{}.pth'.format(save_time,  self.step, extra)))

    def load_state(self, conf, fixed_str, from_save_folder=False, model_only=False):
        if from_save_folder:
            save_path = conf.save_path
        else:
            save_path = conf.model_path

        self.model.load_state_dict(torch.load(
            save_path/'model_{}'.format(fixed_str), map_location=conf.device))
        if not model_only:
            self.head.load_state_dict(torch.load(
                save_path/'head_{}'.format(fixed_str)))
            self.optimizer.load_state_dict(torch.load(
                save_path/'optim_{}'.format(fixed_str)))

    def train(self, conf, epochs):
        self.model.train()
        current_loss = 0.
        for e in range(epochs):
            print('epoch {} started'.format(e))
            if e in self.e_to_update_lr:
                self.update_lr()

            for imgs, labels in tqdm(iter(self.loader)):
                imgs = imgs.to(conf.device)
                labels = labels.to(conf.device)
                self.optimizer.zero_grad()
                # trich xuat
                embeddings = self.model(imgs)
                # arcface
                thetas = self.head(embeddings, labels)

                # CALCULATE LOSS USING CE
                loss = conf.ce_loss(thetas, labels)
                loss.backward()
                current_loss += loss.item()
                self.optimizer.step()

                # cap nhat loss
                if self.step % self.log_loss_per == 0 and self.step != 0:
                    loss_log = current_loss / self.log_loss_per
                    self.log_info_loss_train(loss_log, self.step, e, conf)
                    current_loss = 0.

                if self.step % self.save_per == 0 and self.step != 0:
                    # save model de su dung lai va tien hanh danh gia sau de tiet kiem thoi gian train
                    self.save_state(conf, self.step)

                self.step += 1

        # Luu file final
        self.save_state(conf, self.step, to_save_folder=True, extra='final')

    def update_lr(self):
        for params in self.optimizer.param_groups:
            params['lr'] /= 10

    def infer(self, conf, faces, target_embs, tta=False):
        '''
        faces : list of PIL Image
        target_embs : [n, 512] computed embeddings of faces in facebank
        names : names of faces in facebank
        tta : test time augmentation (hflip)
        '''
        embs = []
        ss = datetime.now()
        print("start recog: ", ss)
        for img in faces:
            # #resize image to 112×112 using PIL (kich thuoc cua file dung de sinh embedding)
            img = Image.fromarray(np.uint8(img)).resize((112, 112))
            if tta:
                mirror = trans.functional.hflip(img)
                emb = self.model(conf.test_transform(
                    img).to(conf.device).unsqueeze(0))
                emb_mirror = self.model(conf.test_transform(
                    mirror).to(conf.device).unsqueeze(0))
                embs.append(l2_norm(emb + emb_mirror))
            else:
                embs.append(self.model(conf.test_transform(
                    img).to(conf.device).unsqueeze(0)))
        source_embs = torch.cat(embs)

        # similarity = nn.CosineSimilarity()
        # dist = similarity(source_embs.unsqueeze(-1),target_embs.transpose(1,0).unsqueeze(0))
        
        diff = source_embs.unsqueeze(-1) - \
            target_embs.transpose(1, 0).unsqueeze(0)
        dist = torch.sum(torch.pow(diff, 2), dim=1)

        minimum, min_idx = torch.min(dist, dim=1)
        min_idx[minimum > self.threshold] = -1  

        ff = datetime.now()
        print("finish recog: ", ff)
        print("total time to recog: ", ff-ss)

        return min_idx, minimum


