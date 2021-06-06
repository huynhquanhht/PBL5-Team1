import datetime
import os
import cv2

class camera:
    def __init__(self):
        return
    def shot_image(self):
        img_path_list = []
        i=0
        cap= cv2.VideoCapture(0, cv2.CAP_DSHOW)
        time = datetime.datetime.now()
        time_str = str(time.year) + "." + str(time.month) + "." + str(time.day) + " " + str(time.hour) + "." + str(time.minute) +"." + str(time.second)
        os.mkdir(time_str)
        while(cap.isOpened()):
            i += 1
            ret, frame = cap.read()
            image_path = time_str + "/img" + str(i) + ".jpg"
            img_path_list.append(image_path)
            cv2.imwrite(image_path, frame)
            if i == 10:
                break
        return img_path_list

if __name__ == '__main__':
    camera = camera()
    img_path_list = []
    img_path_list = camera.shot_image()
