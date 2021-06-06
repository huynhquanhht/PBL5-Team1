# Import libraries
import datetime
import os
import cv2
import numpy as np

class face_detection:
    def __init__(self):
        return

    def get_output_layers(self, net):
        layer_names = net.getLayerNames()
        output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
        return output_layers

    def detect(self):
        # Step 1" Input Image
        image = cv2.imread("object-detection.jpg")
        Width = image.shape[1]
        Height = image.shape[0]
        scale = 0.00392

        # Step 2: Load model and file .config
        # Read deep learning network represented in one of the supported formats.
        # Model: .weights file - Binary file contains trained weights.
        # .cfg: Text file contains network configuration

        net = cv2.dnn.readNet("yolov4-custom_6000.weights", "yolov4-custom.cfg") # Edit WEIGHT and CONFIC file

        # Step 3: Preprocess image (Mean subtraction and scale)
        blob = cv2.dnn.blobFromImage(image, scale, (416, 416), (0, 0, 0), True, crop=False)

        # set input for network
        net.setInput(blob)
        outs = net.forward(self.get_output_layers(net))
        class_ids = []
        confidences = []
        boxes = []
        conf_threshold = 0.2
        nms_threshold = 0.4

        for out in outs:
            for detection in out:
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                if confidence > 0.05:
                    center_x = int(detection[0] * Width)
                    center_y = int(detection[1] * Height)
                    w = int(detection[2] * Width)
                    h = int(detection[3] * Height)
                    x = center_x - w / 2
                    y = center_y - h / 2
                    class_ids.append(class_id)
                    """if confidence < 0.6:
                        class_ids.append(2)""" #change
                    confidences.append(float(confidence))
                    boxes.append([x, y, w, h])

        indices = cv2.dnn.NMSBoxes(boxes, confidences, conf_threshold, nms_threshold)
        time = datetime.datetime.now()
        time_str = str(time.year) + "." + str(time.month) + "." + str(time.day) + " " + str(time.hour) + "." + str(time.minute) +"." + str(time.second)
        os.mkdir(time_str)
        count = 0
        for i in indices:
            i = i[0]
            box = boxes[i]
            x = box[0]
            y = box[1]
            w = box[2]
            h = box[3]
            count = count + 1
            crop_img = image[round(y):round(y + h), round(x):round(x + w)]
            cv2.imwrite(time_str + "/img" + str(count) + ".jpg", crop_img)

if __name__ == "__main__":
    detect = face_detection()
    detect.detect()