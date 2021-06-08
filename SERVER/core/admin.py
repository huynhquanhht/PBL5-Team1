from django.contrib import admin
from core.model.Student import Student
from core.model.Account import Account
from core.model.Attendance import Attendance
from core.model.Info import Info
from core.model.Schedule import Schedule
# Register your models here.

admin.site.register(Student)
admin.site.register(Account)
admin.site.register(Attendance)
admin.site.register(Info)
admin.site.register(Schedule)