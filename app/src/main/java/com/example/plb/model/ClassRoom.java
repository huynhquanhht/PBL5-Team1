package com.example.plb.model;

public class ClassRoom {

    private String name;

    private String time;

    private int countStudent;

    public ClassRoom(String name, String room, String time, int countStudent) {
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

    public int getCountStudent() {
        return countStudent;
    }

    public void setCountStudent(int countStudent) {
        this.countStudent = countStudent;
    }
}
