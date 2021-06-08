print("MAIN_DETECT_RECOG>FILE")
import shutil
import datetime
import numpy as np
from detect_recog.config.config import get_config
from pathlib import Path
from detect_recog.detect.YOLOv4_detect import face_detection 
from detect_recog.recognition.recog.recog import recog
from easydict import EasyDict as edict
from detect_recog.recognition.recog.recog import load_embedding_file, load_learner

from datetime import date, datetime
def detect_and_recog( class_name, update_embed=False):
    start_process = datetime.now()
    print("START PROCESS: ",start_process)
    conf = get_config(training = False)
    args = edict()
    args.class_name = class_name
    args.threshold = conf.threshold
    args.update = update_embed

    start_load_embbed = datetime.now()
    print("Start load embedd: ",start_load_embbed)
    learner = load_learner(conf)
    embeddings, names = load_embedding_file(conf,class_name,learner,args)
    finish_load_embbed = datetime.now()
    print("Finish load embedd: ",finish_load_embbed)
    print("Total time load embedd: ", finish_load_embbed-start_load_embbed)
    print()
   
    rs = set()
    detect = face_detection(args.class_name, conf)
    
    _recog = recog( learner, embeddings,names, conf,args)
    tmp = np.array([])
    start_time = datetime.now()
    time_str = str(start_time.year) + "." + str(start_time.month) + "." + str(start_time.day) + \
        "_" + str(start_time.hour) + "." + str(start_time.minute) + \
        "." + str(start_time.second)
    input_dir = conf.input_face_detect/class_name/'image'
    sub_dir = input_dir/time_str
    if not sub_dir.is_dir():
        sub_dir.mkdir(parents=True)
    for image_dir in input_dir.iterdir():
        if image_dir.is_file():
            try:
                shutil.move(str(image_dir),sub_dir)
            except:
                continue
    input_dir = sub_dir
    # input_dir = Path(r"E:\PBL5\detect_recog\input_face\detect\18Nh13\image\2021.5.29_23.44.15") #DELETE
    list_time_detect = []
    for image_dir in input_dir.iterdir():
        print(image_dir) #DELETE
        if image_dir.is_file():



            start_detect_time = datetime.now()
            print("Start detect: ",start_detect_time)
            time_detect = detect.detect(img_path=image_dir)
            finish_detect_time = datetime.now()
            print("Finish detect: ",finish_detect_time)
            print("Total time detect: ",finish_detect_time-start_detect_time)
            print()



            list_time_detect.append(time_detect)

            ss = datetime.now()
            print("BAT DAU QUA TRINH RECOG: ",ss)
            set_of_attendance_student = _recog.recog(time_detect,time_str)  
            ff = datetime.now()
            print("KET THUC QUA TRINH RECOG: ",ss)
            print("TONG THOI GIAN QUA TRINH RECOG: ", ff-ss)
            print()

            
            if   set_of_attendance_student is None:
                continue     
            tmp = np.hstack((tmp,set_of_attendance_student))
            
    rs = set(tmp.tolist())
    list_path_image = []
    dict_path = dict()     
    for name in rs:   
        input_dir = conf.output_face_recog/class_name/time_str/name
        for img_dir in input_dir.iterdir():
            if img_dir.is_file():
                id, _, dist,_ = str(img_dir).split('\\')[-1].split('_')
                dist = float(dist)
                if id in dict_path:
                    if dict_path[id][0]>dist:
                        dict_path[id] = [dist, img_dir]
                else:
                    dict_path[id] = [dist, img_dir]

    for p in dict_path:
        print(dict_path[p])
        path = str(dict_path[p])[73::].replace("\\",'/')
        out_dir = "http://103.151.123.96:80/image/{}".format(path)
        list_path_image.append(out_dir)

    finish_process = datetime.now()
    print("FINISH PROCESS: ",finish_process)
    print("TOTAL TIME: ",finish_process -start_process)
    print()
    return rs,list_path_image


# if __name__=='__main__':
#     rs, list_path_image = detect_and_recog('18Nh13')
  
#     print("LEN: ",len(list_path_image))
#     for index, name in  enumerate(rs):
#         print(name.split('_')[0], list_path_image[index])
    