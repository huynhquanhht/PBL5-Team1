from django.db import models

class Account(models.Model):
    user = models.TextField(default='',unique=True)
    password = models.TextField(default='')
    infor = models.ForeignKey('core.Info', on_delete=models.CASCADE,null=True)

    def __str__(self):
        return self.user