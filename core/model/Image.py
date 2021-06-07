from django.db import models

class MyFile(models.Model):
    file = models.FileField(upload_to="input_face/detect/18Nh13/image",blank=False,null=False)
    description = models.CharField(default='%d.%m.%y_%H.%M',max_length=255,blank=True,null=True)
    #upload_date = models.DateTimeField(auto_now_add=True)