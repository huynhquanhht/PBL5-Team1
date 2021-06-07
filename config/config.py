import torch
from pathlib import Path
from torch.nn import CrossEntropyLoss
from easydict import EasyDict as edict
from torchvision import transforms as trans

def get_config(training = True):
    conf = edict()
    conf.root_path = Path(".").cwd()

    #recog 
    conf.root_recog_path = conf.root_path/'recognition'
    conf.recog_data_path = conf.root_recog_path/'data'
    conf.recog_train_path =  conf.root_recog_path/'train'
    conf.work_path =  conf.recog_train_path/'work_space'
    conf.log_path = conf.work_path/'log'
    conf.save_path = conf.work_path/'save'
    conf.model_path = conf.work_path/'models'


    # in/out
    conf.input_face_path = conf.root_path/'input_face'
    conf.ouput_face_path = conf.root_path/'output_face'


    # detect
    conf.detect_code = conf.root_path/'detect'
    conf.input_face_detect = conf.input_face_path/'detect'
    # detect xong thi anh luu vao input cua recog
    conf.output_face_detect = conf.input_face_path/'recog'


    # recog
    conf.recog_code = conf.root_path/'recog'
    conf.input_face_recog = conf.output_face_detect
    # conf.detect_data = conf.detect_code/'data' 
    conf.output_face_recog = conf.ouput_face_path/'recog'
    conf.embed_path = conf.recog_data_path/'embed'

    # file weight and cfg of detect 
    conf.detect_weight = str(conf.detect_code/"yolov4-custom_6000.weights")
    conf.detect_cfg = str(conf.detect_code/"yolov4-custom.cfg")



    conf.embedding_size = 512
    conf.input_size = [112,112]
    conf.device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
   
    conf.test_transform = trans.Compose([
                    trans.ToTensor(),
                    trans.Normalize([0.5, 0.5, 0.5], [0.5, 0.5, 0.5])
                ])

    
    conf.emore_folder = conf.recog_data_path/'faces_emore'
    conf.batch_size = 200 

    # Recog Training Config 
    if training:
        conf.epochs = 20
        conf.log_path = conf.work_path/'log'
        conf.save_path = conf.work_path/'save'
        conf.ce_loss = CrossEntropyLoss()
        conf.lr = 1e-3
        conf.e_to_update_lr = [3, 7, 15, 18]
        conf.momentum = 0.9
        conf.num_workers = 6
        conf.pin_memory = True
        
        
    # Recog Inference Config
    else:
        conf.facebank_path = conf.recog_data_path/'facebank'
        conf.threshold = 1.5

    return conf