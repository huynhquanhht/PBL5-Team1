from django.contrib import admin
from django.urls import path, include

from core.views import login,checkStudent,getInfo,getSchedule,\
    getHistoryClass,getHistory,getStudentLoginAttend,getAccountId,\
    getStudentResult,getStudentAttend,home,createStudent,updateStudent,\
    MyFileView, getImage, attendance,checkrasp,createSchedule,\
    getHistoryGV,createAttend,updateAttend

urlpatterns = [
    path('api-auth/', include('rest_framework.urls')),
    path('admin/', admin.site.urls),
    path('login/<str:user>&<str:password>', login),
    path('account/<str:username>/',getAccountId),
    path('student/create/',createStudent),
    path('student/<int:id>/', getStudentResult),
    path('student/<str:id>&<str:codeclass>/', getStudentLoginAttend),
    path('student/attend/<int:id>/', getStudentAttend),
    path('student/update/<str:codestudent>&<int:scheduleid>&<int:attendid>/',updateStudent),
    path('checkStudent/<str:codestudent>&<str:idschedule>', checkStudent),
    path('info/<str:id>/', getInfo),
    path('class/', getHistory),
    path('class/<str:id>/', getHistoryClass),
    path('class/gv/<str:id>/', getHistoryGV),
    path('schedule/<str:id>&<str:serial>/', getSchedule),
    path('schedule/create/',createSchedule),
    path('upload/', MyFileView.as_view()),
    path('attendance/<str:scheduleid>&<str:attendid>/', attendance),
    path('attend/create/',createAttend),
    path('attend/update/<str:id>/',updateAttend),
    path('checkrasp/', checkrasp),
]
