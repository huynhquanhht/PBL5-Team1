from django.db import models

class Info(models.Model):
    name = models.TextField(default='')
    email = models.TextField(default='')
    phone = models.TextField(default='')
    sex = models.TextField(default='true')
    birthday = models.TextField(default='')
    url = models.TextField(default='')

    def __str__(self):
        return self.name