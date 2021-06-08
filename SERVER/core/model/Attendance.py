from django.db import models

class Attendance(models.Model):
    timeattend = models.TextField(default='')   
    total = models.TextField(default='')
    status = models.TextField(default='',null=True,blank=True)
    schedule = models.ForeignKey('core.Schedule', on_delete=models.CASCADE,related_name='scheduler', null=True,blank=True)