#%%
print("MAIN.FILE")
import datetime
import os
import cv2
import numpy as np
import argparse
from config.config import get_config
from detect.YOLOv4_detect import face_detection 
from recognition.recog.recog import recog
from easydict import EasyDict as edict
 
from datetime import datetime


def detect_and_recog(class_name, update_embed=False):
    conf = get_config(training = False)

    args = edict()
   
    args.class_name = class_name
    args.threshold =  conf.threshold
    args.update = update_embed
 

    detect = face_detection(args.class_name, conf)

    start_detect_time = datetime.now()
    print("Start_detect_time: ",start_detect_time)
    time_detect = detect.detect()
    finish_detect_time = datetime.now()
    print("Finish_detect_time: ",finish_detect_time)
    print("Total time detect: ", finish_detect_time - start_detect_time)
    print()
    print("time_detect",time_detect)

    s = datetime.now()
    print("BAT DAU RECOG: ",s)
    m_recog = recog(conf,args)
    set_of_attendance_student = m_recog.recog(time_detect)
    f = datetime.now()
    print("KET THUC RECOG: ",f)
    print("CA QUA TRINH MAT: ",f-s)


    print("set_of_attendance_student")
    print(set_of_attendance_student)
    print(len(set_of_attendance_student))
    return set_of_attendance_student

if __name__ == '__main__':
    detect_and_recog('18Nh13', update_embed=False)
# %%
