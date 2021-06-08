# Generated by Django 2.2.4 on 2021-05-28 20:14

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0007_auto_20210529_0310'),
    ]

    operations = [
        migrations.AlterField(
            model_name='myfile',
            name='description',
            field=models.CharField(blank=True, default='%d.%m.%y_%H.%M.%S', max_length=255, null=True),
        ),
        migrations.AlterField(
            model_name='myfile',
            name='file',
            field=models.FileField(upload_to='input_face/detect/18Nh13/image/%d.%m.%y_%H.%M.%S'),
        ),
    ]
