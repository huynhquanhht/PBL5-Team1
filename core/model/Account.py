from django.db import models
#from core.model import Info

class Account(models.Model):
    #id = models.IntegerField(primary_key=True, max_length=255)
    user = models.TextField(default='',unique=True)
    password = models.TextField(default='')
    infor = models.ForeignKey('core.Info', on_delete=models.CASCADE,null=True)

    def __str__(self):
        return self.user