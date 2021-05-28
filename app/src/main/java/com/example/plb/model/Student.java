package com.example.plb.model;

import java.io.Serializable;
import java.util.Comparator;

public class Student implements Serializable {

    private String id;

    private String codeStudent;

    private String name;

    private String phone;

    private String birthDay;

    private boolean sex;

    private String baseClass;

    private String urlAvatar;

    private String urlAttend;

    private int status;

    private String totalAbsent;

    private String idSchedule;

    private String idAttendance;

    public Student(String id, String codeStudent, String name, String phone, String birthDay, boolean sex, String baseClass, String urlAvatar, String urlAttend, int status, String totalAbsent, String idSchedule, String idAttendance) {
        this.id = id;
        this.codeStudent = codeStudent;
        this.name = name;
        this.phone = phone;
        this.birthDay = birthDay;
        this.sex = sex;
        this.baseClass = baseClass;
        this.urlAvatar = urlAvatar;
        this.urlAttend = urlAttend;
        this.status = status;
        this.totalAbsent = totalAbsent;
        this.idSchedule = idSchedule;
        this.idAttendance = idAttendance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeStudent() {
        return codeStudent;
    }

    public void setCodeStudent(String codeStudent) {
        this.codeStudent = codeStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getUrlAttend() {
        return urlAttend;
    }

    public void setUrlAttend(String urlAttend) {
        this.urlAttend = urlAttend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(String totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getIdAttendance() {
        return idAttendance;
    }

    public void setIdAttendance(String idAttendance) {
        this.idAttendance = idAttendance;
    }

    public static Comparator<Student> FruitNameComparator
            = new Comparator<Student>() {

        public int compare(Student fruit1, Student fruit2) {

            String fruitName1 = fruit1.getTotalAbsent().toUpperCase();
            String fruitName2 = fruit2.getTotalAbsent().toUpperCase();

            return fruitName1.compareTo(fruitName2);
        }

    };

}
