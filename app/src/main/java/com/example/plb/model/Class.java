package com.example.plb.model;

public class Class {

    private String name;

    private String time;

    private String countStudent;

    public Class(String name, String time, String countStudent) {
        this.name = name;
        this.time = time;
        this.countStudent = countStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCountStudent() {
        return countStudent;
    }

    public void setCountStudent(String countStudent) {
        this.countStudent = countStudent;
    }
}
