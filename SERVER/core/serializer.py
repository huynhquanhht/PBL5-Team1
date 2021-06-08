from rest_framework import serializers
from .model.Student import Student
from .model.Account import Account
from .model.Attendance import Attendance
from .model.Info import Info
from .model.Schedule import Schedule
from .model.Image import MyFile

class AccountSerializer(serializers.ModelSerializer):
    class Meta:
        model = Account
        fields = '__all__'

class AttendanceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Attendance
        fields = '__all__'

class InfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Info
        fields = '__all__'

class ScheduleSerializer(serializers.ModelSerializer):
    class Meta:
        model = Schedule
        fields = '__all__'

class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = '__all__'

#studentattend
class StudentAtendanceSerializer(serializers.ModelSerializer):
    timeattend = serializers.SerializerMethodField()
    subject = serializers.SerializerMethodField()
    room = serializers.SerializerMethodField()

    @staticmethod
    def get_timeattend(obj):
        timeattend = Attendance.objects.filter(attendances=obj).first()
        if timeattend:
            serializer = AttendanceSerializer(timeattend)
            return serializer.data['timeattend']
        return None
    
    @staticmethod
    def get_subject(obj):
        subject = Schedule.objects.filter(schedules=obj).first()
        if subject:
            serializer = ScheduleSerializer(subject)
            return serializer.data['subject']
        return None
    
    @staticmethod
    def get_room(obj):
        room = Schedule.objects.filter(schedules=obj).first()
        if room:
            serializer = ScheduleSerializer(room)
            return serializer.data['room']
        return None

    class Meta:
        model = Student
        fields = ['name','urlavatar','baseclass','subject','room','schedule_id','timeattend','status']


class MyFileSerializer(serializers.ModelSerializer):
    class Meta():
        model = MyFile
        fields = ('file', 'description')

#attendancehistory
class AttendanceGVSerializer(serializers.ModelSerializer):
    codeclass = serializers.SerializerMethodField()
    subject = serializers.SerializerMethodField()

    @staticmethod
    def get_codeclass(obj):
        codeclass = Schedule.objects.filter(scheduler=obj).first()
        if codeclass:
            serializer = ScheduleSerializer(codeclass)
            return serializer.data['codeclass']
        return None

    @staticmethod
    def get_subject(obj):
        subject = Schedule.objects.filter(scheduler=obj).first()
        if subject:
            serializer = ScheduleSerializer(subject)
            return serializer.data['subject']
        return None
        
    class Meta:
        model = Attendance
        fields = ['id','timeattend','total','status','codeclass','subject','schedule']