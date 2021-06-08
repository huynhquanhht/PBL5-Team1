from pickle import dumps
from main_detect_recog import detect_and_recog
from core.model.Image import MyFile
from json.encoder import JSONEncoder
from typing import Dict
from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render
import requests, time, json
from datetime import datetime
from rest_framework.views import APIView
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.parsers import JSONParser
from rest_framework import status
from rest_framework.parsers import MultiPartParser, FormParser

from .model.Student import Student
from .model.Account import Account
from .model.Attendance import Attendance
from .model.Info import Info
from .model.Schedule import Schedule

from .serializer import MyFileSerializer, StudentSerializer,AttendanceGVSerializer
from .serializer import AccountSerializer
from .serializer import AttendanceSerializer
from .serializer import InfoSerializer
from .serializer import ScheduleSerializer
from .serializer import StudentAtendanceSerializer

# Create your views here.
@api_view(['GET'])
def home(request, codestudent,scheduleid,attendid):
    student = Student.objects.filter(codestudent=codestudent, schedule_id=int(scheduleid), attendance_id=int(attendid))
    serializer = StudentSerializer(student, many=True)
    return Response(serializer.data)

#login
@api_view(['GET'])
def login(request, user,password):
    now = datetime.now()
    account = Account.objects.filter(user=user, password=password)
    serializer = AccountSerializer(account, many=True)
    if account.count() == 0:
        then=datetime.now()
        print(then-now)
        return Response(False)
    else:     
        then=datetime.now()
        print(then-now)
        return Response(serializer.data)

#checkStudent
@api_view(['GET'])
def checkStudent(request, codestudent, idschedule):
    student = Student.objects.filter(codestudent=codestudent, schedule_id=idschedule,attendance_id__isnull=False)
    if(student.count()==0):
        a = json.dumps(False)
    else:
        a = json.dumps(True) 
    return Response(a)

#getInfo
@api_view(['GET'])
def getInfo(request, id):
    info = Info.objects.filter(id=id).values()
    #info = Info.objects.raw('SELECT * FROM core_info WHERE id=%s',id)
    serializer = InfoSerializer(info, many=True)
    if info.count():
        return Response(serializer.data)
    elif info.count()==0:
        return Response(False)

#getHistory
@api_view(['GET'])
def getHistory(request):
    student = Student.objects.prefetch_related('attendance').prefetch_related('schedule')
    serializer = StudentAtendanceSerializer(student, many=True)
    if student.count():
        return Response(serializer.data)
    elif student.count()==0:
        return Response(False)

#getHistoryClass
@api_view(['GET'])
def getHistoryClass(request, id):
    attendance = Attendance.objects.filter(schedule_id=id)
    serializer = AttendanceGVSerializer(attendance, many=True)
    if attendance.count():
        return Response(serializer.data)
    elif attendance.count()==0:
        return Response(False)

#getHistoryGV
@api_view(['GET'])
def getHistoryGV(request, id):
    attendance = Attendance.objects.filter(schedule__account_id=id)
    serializer = AttendanceGVSerializer(attendance, many=True)
    if attendance.count():
        return Response(serializer.data)
    elif attendance.count()==0:
        return Response(False)

#getAccountID
@api_view(['GET'])
def getAccountId(request, username):
    account = Account.objects.filter(user=username)
    serializer = AccountSerializer(account, many=True)
    if account.count():
        return Response(serializer.data[0]['id'])
    elif account.count()==0:
        return Response(False)

#getSchedule
@api_view(['GET'])
def getSchedule(request, id, serial):
    if serial == ' ':
        schedule = Schedule.objects.filter(account_id=id)
    else :
        schedule = Schedule.objects.filter(account_id=id, serial=serial)
    serializer = ScheduleSerializer(schedule, many=True)
    if schedule.count():
        return Response(serializer.data)
    elif schedule.count()==0:
        return Response(False)

#createSchedule
@api_view(['POST'])
def createSchedule(request):
    serializer = ScheduleSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data['id'])
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#updateSchedule
@api_view(['PATCH'])
def updateSchedule(request, id):
    schedule = Schedule.objects.get(id=id)
    serializer = ScheduleSerializer(instance=schedule, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)    

#createAttendance
@api_view(['POST'])
def createAttend(request):
    serializer = AttendanceSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data['id'])
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#getStudentLoginAttend
@api_view(['GET'])
def getStudentLoginAttend(request, id, codeclass):
    student = Student.objects.prefetch_related('attendance').prefetch_related('schedule').filter(attendance_id__isnull=False, baseclass=codeclass, codestudent=id)
    serializer = StudentAtendanceSerializer(student, many=True)
    if student.count():
        return Response(serializer.data)
    elif student.count()==0:
        return Response(False)

#getStudentResult
@api_view(['GET'])
def getStudentResult(request, id):
    student = Student.objects.filter(attendance_id=id).order_by('-status')
    serializer = StudentSerializer(student, many=True)
    if student.count():
        return Response(serializer.data)
    elif student.count()==0:
        return Response(False)

#getStudentAttend
@api_view(['GET'])
def getStudentAttend(request,id):
    student = Student.objects.filter(schedule_id=id).order_by("codestudent")
    serializer = StudentSerializer(student, many=True)
    if student.count():
        return Response(serializer.data)
    elif student.count()==0:
        return Response(False)

#createStudent
@api_view(['POST'])
def createStudent(request):
    serializer = StudentSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data['id'])
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#updateStudentStatus
@api_view(['PATCH'])
def updateStudent(request, codestudent,scheduleid,attendid):
    student = Student.objects.get(codestudent=codestudent,schedule=int(scheduleid),attendance=int(attendid))
    serializer = StudentSerializer(instance=student, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)    
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#updateAttend
@api_view(['PATCH'])
def updateAttend(request, id):
    attendance = Attendance.objects.get(id=id)
    serializer = AttendanceSerializer(instance=attendance, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)    
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#uploadImage
class MyFileView(APIView):
    parser_classes = (MultiPartParser, FormParser)
    def post(self, request, *args, **kwargs):
	    file_serializer = MyFileSerializer(data=request.data)
	    if file_serializer.is_valid():
		    file_serializer.save()
		    return Response(file_serializer.data, status=status.HTTP_201_CREATED)
	    else:
		    return Response(file_serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#getAllImage
@api_view(['GET'])
def getImage(request):
    image = MyFile.objects.all()
    serializer = MyFileSerializer(image, many=True)
    return Response(serializer.data)


checkrb=False
#check
@api_view(['GET'])
def checkrasp(request):
    global checkrb
    if checkrb==False:
        return Response(False)
    elif checkrb==True:
        return Response(True)

#attendance
@api_view(['GET'])
def attendance(request, scheduleid,attendid):
    now = datetime.now()
    schedule = Schedule.objects.filter(id=scheduleid)
    serializer = ScheduleSerializer(schedule, many=True)
    class_name = serializer.data[0]['codeclass']
    total = serializer.data[0]['total']
    global checkrb
    checkrb=True
    time.sleep(5)
    checkrb=False
    time.sleep(10)
    rs, list_path_image = detect_and_recog(class_name)
    attend=len(rs)
    for index, name in enumerate(rs):
        idstudent = name.split('_')[0]
        url = list_path_image[index]
        requests.patch("http://localhost:8000/student/update/%s&%s&%s/" %(idstudent,scheduleid,attendid), data={'status': '0', 'urlattend': url})
    then=datetime.now()
    print(then-now)
    return Response(int(total)-int(attend))