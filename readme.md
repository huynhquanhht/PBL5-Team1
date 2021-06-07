## Bước 1: Chuẩn bị Dataset
+ Link dataset: https://github.com/deepinsight/insightface/wiki/Dataset-Zoo</br>
+ Download dataset và giải nén tất cả file bên trong vào thư mục <b>recognition/data/faces_emore</b>, ta sẽ có cây thư mục như sau:<br/>
<pre>
+----data
	+----faces_emore
        +----train.rec
        +----train.idx
        +----agedb_30.bin
        +----cfp_ff.bin
        +----cfp_fp.bin
        +----cfplw.bin
        +----lfw.bin
        +----vgg2_fp.bin
        +----property
</pre>
+ Run: <code>python train/extract.py</code> để giải nén dữ liệu (quá trình này sẽ mất kha khá thời gian)



## Bước 2: Config
+ Tại thư mục <b>recognition/config/</b>, chỉnh sửa nội dung file <b>config.py</b> cho phù hợp



## Bước 3: Train
+ Run: <code>python train.py</code>, để huấn luyện mô hình</br>
+ Trong quá trình train sẽ lưu lại các </b>file weight</b> tại một số thời điểm để tiện cho việc train lại, các file này được lưu tại <b>recognition/train/workspace/models</b></br>
+ File weight sau khi hoàn tất train sẽ lưu tại <b>recognition/train/work_space/save</b>


## Bước 4: Chuẩn bị dữ liệu để tạo embedding vector gốc
Dữ liệu là các hình ảnh được cấu trúc như sau, số lượng ảnh không giới hạn:
<pre>
+----Recognition
	+----Data
		+----facebank
			+----ID1
				+----Name_1.jpg
				+----Name_2.jpg
				+----Name_n.jpg
			+----ID2
				+----Name_1.jpg
				+----Name_2.jpg
				+----Name_n.jpg
</pre>
+ File embedding được lưu ở thư mục <b>recognition/data/embed/<class_name></b> gồm 2 file: <b>facebank.pth</b> và <b>names.npy</b></br>
+ File embedding được tự động tạo khi bị thiếu file names.py hoặc facebank.pth </br>
+ Để yêu cầu cập nhật facebank, set tham số <b>update_embed</b> của hàm <b>detect_and_recog(<class_name>,<update_embed=False>)</b> thành <b>True</b>



## Bước 5: Nhận diện
+ Lưu ảnh muốn nhận ở thư mục <b>recognition/input_face/detect/>class_name>/<file_name.jpg></b></br>
+ Chỉnh sửa tên file ảnh muốn detect tại hàm detect face của face detector sau đó lưu ảnh được detect vào <b>recognition/input_face/detect/<class_name></b></br>
+ Chọn file weight rồi lưu vào tại <b>recognition/train/workspace/save</b></br>
+ Chỉnh tên file weight trong file <b>recog/recog.py</b> thành tên file weight muốn sử dụng</br>
+ Run: <code>python main.py</code> để tiến hành nhận diện</br>
+ Kết quả được lưu ở <b>recognition/output_face/recog/<class_name>/<time><ID.jpg></b></br>


## Lưu ý

+ Face dectect có thể sử dụng bất kì detector nào mà bạn muốn, chỉ cần xuất ảnh đã detect vào thư mục <b>recognition/input_face/detect/<class_name></b></br>
+ Download face detector Yolov4 [tại đây](https://drive.google.com/drive/folders/1em9K1RhhsA5xULIMqK_jtJsO8XzashcR?usp=sharing) và đặt vào thư mục <b>/detect</b>
+ Nếu gặp bất kì vấn đề nào liên quan đến import , vui lòng run: <code>python setup.py</code>


 

