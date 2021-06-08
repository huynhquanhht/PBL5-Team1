from pathlib import Path
from detect_recog.recognition.func import utils
from detect_recog.recognition.func.Learner import learner
from random import seed
from random import randint
from PIL import Image
seed(1)
def load_learner(conf):
    learner = learner(conf, True)
    learner.load_state(conf, 'final.pth', True, True)
    learner.model.eval()
    return learner

def load_embedding_file(conf,class_name,learner,args):
    embed_path = conf.embed_path/class_name
    if not embed_path.is_dir():
        embed_path.mkdir(parents=True, exist_ok=False)
        print("generating embedding file for {}".format(class_name))
        embeddings, names = utils.prepare_facebank_non_face_detector(conf,learner.model, class_name,  tta=True)
        print("finished generating embedding file")
    elif not Path(embed_path/'facebank.pth').is_file() or not Path(embed_path/'names.npy').is_file():
        
        print("generating embedding file for {} because embedding file or names file is missing".format(
            class_name))
        embeddings, names = utils.prepare_facebank_non_face_detector(conf,learner.model, class_name,  tta=True)
        print("finished generating embedding file")
    elif args.update:
        print("updating embedding file for {}".format(class_name))
        embeddings, names = utils.prepare_facebank_non_face_detector(conf,learner.model, class_name,  tta=True)
        print("finished updating embedding file")
    else:
        print("loading embedding file for {}".format(class_name))
        embeddings, names = utils.load_embed(
            conf, class_name)
    return embeddings, names

class recog():
    def __init__(self, learner, embeddings, names, conf, args):
        self.conf = conf
        self.args = args
        self.class_name = args.class_name
        self.learner = learner
        self.embed_path = conf.embed_path/self.class_name
        self.embeddings, self.names = embeddings, names
        
    def recog(self, time_detect,time_str):
        input_dir = self.conf.input_face_recog/self.args.class_name/time_detect
        class_name = self.class_name
        output_face_recog = self.conf.output_face_recog/class_name/time_str
        results, score = utils.inference_non_face_detector(
            self.conf, self.args, self.embeddings, self.names, self.learner, time_detect)
        if results is None and score is None:
            return None
        
       

        for i, img_path in enumerate(input_dir.iterdir()):
            if img_path.is_file():
                img = Image.open(img_path)
                index = results[i]
                rand_num = randint(0, 1000)
                name_folder = self.names[index]
                member_face_output = output_face_recog/name_folder
                if not member_face_output.is_dir():
                    member_face_output.mkdir(parents=True)
                file_name = "{}_{}_{}.jpg".format(name_folder,score[i],rand_num)
                file_path = member_face_output/file_name
                img = img.save(file_path)
               
        s= self.names[results]
        return s
  
