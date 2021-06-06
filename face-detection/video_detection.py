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
        cap = cv2.VideoCapture('Vid5.mp4')
        # write_frame = cv2.VideoWriter('Video/Quan_new.mp4', fourcc, 20, (640, 480))
        frame_width = int(cap.get(3))
        frame_height = int(cap.get(4))
        write_frame = cv2.VideoWriter('Vid5_new.avi', cv2.VideoWriter_fourcc(*'XVID'), 20.0, (frame_width, frame_height))

        while(True):
            ret, image = cap.read()
            Width = image.shape[1]
            Height = image.shape[0]
            scale = 0.00392
            net = cv2.dnn.readNet("yolov4-custom_2000(1).weights", "yolov4-custom.cfg") # Edit WEIGHT and CONFIC file

            # Step 3: Preprocess image (Mean subtraction and scale)
            blob = cv2.dnn.blobFromImage(image, scale, (416, 416), (0, 0, 0), True, crop=False)
            # get preprocessed image to net

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
                        confidences.append(float(confidence))
                        boxes.append([x, y, w, h])

            indices = cv2.dnn.NMSBoxes(boxes, confidences, conf_threshold, nms_threshold)
            for i in indices:
                i = i[0]
                box = boxes[i]
                x = box[0]
                y = box[1]
                w = box[2]
                h = box[3]
                cv2.rectangle(image, (round(x), round(y)), (round(x + w), round(y + h)), (0, 0, 255), 3)
            cv2.imshow('Frame', image)
            write_frame.write(image)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break;
        cap.release()
        cv2.destroyAllWindows()

if __name__ == "__main__":
    detect = face_detection()
    detect.detect()
