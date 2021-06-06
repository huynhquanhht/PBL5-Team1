


def draw_prediction(img, class_id, confidence, x, y, x_plus_w, y_plus_h):
    label = str(classes[class_id])
    color = COLORS[class_id]
    cv2.rectangle(img, (x, y), (x_plus_w, y_plus_h), color, 2)
    cv2.putText(img, label + str(confidence) , (x - 10, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)

def savePredict(name, text):
    textName = name + '.txt'
    with open(textName, 'w+') as groundTruth:
        groundTruth.write(text)
        groundTruth.close()

img_path = 'img.jpg'
image = cv2.imread("diep.jpg") # đổi tên ảnh để nhận dạng
cv2.imshow('image',image)
cv2.waitKey(0)
cv2.destroyAllWindows()

Width = image.shape[1]
Height = image.shape[0]
scale = 0.00392

classes = None
with open("yolo.names", 'r') as f: # Chỉnh sửa file yolo.names ở đây
    classes = [line.strip() for line in f.readlines()]
    COLORS = np.random.uniform(0, 255, size=(len(classes), 3))
    net = cv2.dnn.readNet("yolov3-tiny_6000.weights", "yolov3-tiny.cfg")
    #Thay đổi tên của file weights và file cfg tại đây. blob = cv2.dnn.blobFromImage(image, scale, (416, 416), (0, 0, 0), True, crop=False) #Convert ảnh sang blob

net.setInput(blob)
outs = net.forward(get_output_layers(net))
#print(outs)
class_ids = []
confidences = []
boxes = []
conf_threshold = 0.5 # Đây là ngưỡng vật thể, nếu xác suất của vật thể nhỏ hơn 0.5 thì #model sẽ loại bỏ vật thể đó. Các bạn có thể tăng lên cao hoặc giảm xuống tùy theo mục #đích model của mình.
nms_threshold = 0.4
#Nếu có nhiều box chồng lên nhau, và vượt quá giá trị 0.4 (tổng diện tích chồng nhau) thì #1 trong 2 box sẽ bị loại bỏ.
start = time.time() #đo thời gian thực thi của model