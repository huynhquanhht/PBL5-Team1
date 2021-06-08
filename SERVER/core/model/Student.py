from django.db import models

class Student(models.Model):
    codestudent = models.TextField(default='')
    name = models.TextField(default='')
    phone = models.TextField(default='')
    birthday = models.TextField(default='')
    sex = models.TextField(default='true')
    baseclass = models.TextField(default='')
    status = models.TextField(default='0')
    urlavatar = models.TextField(default='',null=True,blank=True)
    urlattend = models.TextField(default='',null=True,blank=True)
    schedule = models.ForeignKey('core.Schedule', on_delete=models.CASCADE,related_name='schedules',null=True,blank=True)
    attendance = models.ForeignKey('core.Attendance', on_delete=models.CASCADE,related_name='attendances',null=True,blank=True)
    
    def __str__(self):
        return self.name