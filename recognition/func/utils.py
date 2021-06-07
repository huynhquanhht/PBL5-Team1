#coding=utf-8

import torch
import numpy as np
from torchvision import transforms as trans
from PIL import  Image
from datetime import datetime
from recognition.data.preprocess import preprocess

# l2 normalization : reference [https://arxiv.org/pdf/1502.03167.pdf]
def l2_norm(input, axis=1):
    # tinh norm 2 (euclidean distance)
    norm = torch.norm(input, 2, axis, True)
    # Chuan hoa
    output = torch.div(input, norm)
    return output

# 
def extract_bn_para(modules):
    if not isinstance(modules, list):
        modules = [*modules.modules()]
    only_bn = []
    wo_bn = []
    for layer in modules:
        if 'batchnorm' in str(layer.__class__):
            only_bn.extend([*layer.parameters()])
        else:
            wo_bn.extend([*layer.parameters()])
    return only_bn, wo_bn

# infer tu hinh anh duoc detect boi thuat toan detect
def inference_non_face_detector(conf, args, targets, names, learner, detect_time):
 
    learner.threshold = args.threshold
    input_dir = conf.input_face_recog/args.class_name/detect_time
    faces= []
    for img_path in  input_dir.iterdir():
        if img_path.is_file():
            img = Image.open(img_path)
            faces.append(img)
    results, score = learner.infer(conf, faces, targets, tta=True)
    
    return results, score

# Load file embeddings va file names tuong ung
def load_embed(conf,class_name):
    embeddings = torch.load(conf.embed_path/class_name/'facebank.pth')
    names = np.load(conf.embed_path/class_name/'names.npy')
    print('Facebank Loaded')
    return embeddings, names


# format thoi gian hien tai thanh y-m-d-h-m-s
def get_time():
    return (str(datetime.now())[:-7]).replace(' ','-').replace(':','-')


def prepare_facebank_non_face_detector(conf, model, class_name, tta = True):
    model.eval()
    embeddings =  []
    names = []
    facebank_path = conf.facebank_path/class_name
    for path in facebank_path.iterdir():
        if path.is_file():
            continue
        else:
            print(path)
            embs = []
            for file in path.iterdir():
                if not file.is_file():
                    continue
                else:
                    try:
                        img = Image.open(file)
                        if img.size != (112, 112):
                            img = img.resize((112,112))
                    except:
                        continue

                    with torch.no_grad():
                        if tta:
                            mirror = trans.functional.hflip(img)
                            emb = model(conf.test_transform(img).to(conf.device).unsqueeze(0))
                            emb_mirror = model(conf.test_transform(mirror).to(conf.device).unsqueeze(0))
                            embs.append(l2_norm(emb + emb_mirror))
                        else:
                            embs.append(model(conf.test_transform(img).to(conf.device).unsqueeze(0)))
        if len(embs) == 0:
            continue
        embedding = torch.cat(embs).mean(0,keepdim=True)
        embeddings.append(embedding)
        names.append(path.name)
    embeddings = torch.cat(embeddings)
    
    names.append('Unknown')
    names = np.array(names)

    save_path = conf.embed_path/class_name
    if not save_path.is_dir():
        save_path.mkdir(parents=True)
    torch.save(embeddings, conf.embed_path/class_name/'facebank.pth')
    np.save(conf.embed_path/class_name/'names', names)
    return embeddings, names


hflip = trans.Compose([
            preprocess,
            trans.ToPILImage(),
            trans.functional.hflip,
            trans.ToTensor(),
            trans.Normalize([0.5, 0.5, 0.5], [0.5, 0.5, 0.5])
        ])
# lat ngang: dung trong tta
def hflip_batch(imgs_tensor):
    hfliped_imgs = torch.empty_like(imgs_tensor)
    for i, img_ten in enumerate(imgs_tensor):
        hfliped_imgs[i] = hflip(img_ten)
    return hfliped_imgs