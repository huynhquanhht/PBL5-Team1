print("RECOG.FILE")
from pathlib import Path
import os
from recognition.func import utils
from recognition.func.Learner import learner
from PIL import Image
import numpy as np
from datetime import datetime

class recog():
    def __init__(self, conf, args):
        self.conf = conf
        self.args = args
        self.class_name = args.class_name



        start_load_mode = datetime.now()
        print("start_load_model: ", start_load_mode)
        self.learner = learner(conf, True)
        self.learner.load_state(conf, 'final.pth', True, True)
        self.learner.model.eval()
        self.embed_path = conf.embed_path/self.class_name
        finish_load_mode = datetime.now()
        print("finish_load_model: ", finish_load_mode)
        print("total time to load model:", finish_load_mode-start_load_mode)

        # prepare_facebank
        start_load_embed = datetime.now()
        print("start_load_embed: ", start_load_embed)
        if not self.embed_path.is_dir():
            self.embed_path.mkdir(parents=True, exist_ok=False)
            print("generating embedding file for {}".format(self.class_name))
            self.embeddings, self.names = utils.prepare_facebank_non_face_detector(conf,
                                                                                   self.learner.model, self.class_name,  tta=True)
            print("finished generating embedding file")
        elif not Path(self.embed_path/'facebank.pth').is_file() or not Path(self.embed_path/'names.npy').is_file():
            print("generating embedding file for {} because embedding file or names file is missing".format(
                self.class_name))
            self.embeddings, self.names = utils.prepare_facebank_non_face_detector(conf,
                                                                                   self.learner.model, self.class_name,  tta=True)
            print("finished generating embedding file")
        elif args.update:
            print("updating embedding file for {}".format(self.class_name))
            self.embeddings, self.names = utils.prepare_facebank_non_face_detector(conf,
                                                                                   self.learner.model, self.class_name,  tta=True)
            print("finished updating embedding file")
        else:
            print("loading embedding file for {}".format(self.class_name))
            self.embeddings, self.names = utils.load_embed(
                conf, self.class_name)
            print("finished loading embedding file")

        finish_load_embed = datetime.now()
        print("finish_load_embed: ", finish_load_embed)
        print("total time to load embed:", finish_load_embed-start_load_embed)






        
    def recog(self, time_detect):
        input_dir = self.conf.input_face_recog/self.args.class_name/time_detect
        data_dir = self.conf.input_face_recog/self.class_name/time_detect
        class_name = self.class_name
        # facebank_path = conf.facebank_path/class_name
        input_face_recog = self.conf.input_face_recog/class_name
        output_face_recog = self.conf.output_face_recog/class_name/time_detect
        embed_path = self.embed_path

        if not output_face_recog.is_dir():
            output_face_recog.mkdir(parents=True)
      

        results, score = utils.inference_non_face_detector(
            self.conf, self.args, self.embeddings, self.names, self.learner, time_detect)
        print("results")
        print(results)
        
        print("score")
        print(score)
        rs = np.array([])

        for i, img_path in enumerate(input_dir.iterdir()):
            if img_path.is_file():
                img = Image.open(img_path)
                index = results[i]
            
                file_name = "{}_{}.jpg".format(self.names[index],score[i])
                file_path = output_face_recog/file_name
                img = img.save(file_path)
                print(i,self.names[index],file_name )
        rs = np.hstack((rs,self.names[results]))
        s= set(rs)
      
        return s
     
