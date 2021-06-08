print("LEARNER>FILE")
# import math
import torch
import numpy as np

from PIL import Image
from matplotlib import pyplot as plt
from torchvision import transforms as trans
from detect_recog.recognition.func.utils import l2_norm
from detect_recog.recognition.func.mobileFaceNet import MobileFaceNet
from datetime import datetime


class learner(object):
    def __init__(self, conf, inference=False):
      
            
        self.model = MobileFaceNet(conf.embedding_size).to(conf.device)
        print('MobileFaceNet Generated\n')
        
        self.threshold = conf.threshold

    def load_state(self, conf, fixed_str, from_save_folder = False, model_only = False):
        if from_save_folder:
            save_path = conf.save_path
        else:
            save_path = conf.model_path

        self.model.load_state_dict(torch.load(save_path/'model_{}'.format(fixed_str),map_location=conf.device))
        

    def infer(self, conf, faces, target_embs, tta=False):
      
        embs = []
        ss = datetime.now()
        print("Start recog: ",ss)
        for img in faces:
            # #resize image to 112×112 using PIL (kich thuoc cua file dung de sinh embedding)
            img = Image.fromarray(np.uint8(img)).resize((112,112))
            if tta:
                mirror = trans.functional.hflip(img)
                emb = self.model(conf.test_transform(img).to(conf.device).unsqueeze(0))
                emb_mirror = self.model(conf.test_transform(mirror).to(conf.device).unsqueeze(0))
                embs.append(l2_norm(emb + emb_mirror))
            else:
                embs.append(self.model(conf.test_transform(img).to(conf.device).unsqueeze(0)))
        if len(embs) ==0:
            return None, None
        source_embs = torch.cat(embs)

        diff = source_embs.unsqueeze(-1) - target_embs.transpose(1,0).unsqueeze(0)
        dist = torch.sum(torch.pow(diff, 2), dim=1)
        minimum, min_idx = torch.min(dist, dim=1)
        min_idx[minimum > self.threshold] = -1 # if no match, set idx to -1
        
        ff = datetime.now()
        print("Finish recog: ", ff)
        print("Total time recog: ",ff-ss)
        print()
        
        return min_idx , minimum

    

   