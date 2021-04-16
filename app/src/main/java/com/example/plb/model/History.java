package com.example.plb.model;

public class History {
    private String id;
    private String time;
    private String baseclass;
    private String room;
    private int total;
    private int absent;

    public History(String id, String time, String baseclass, String room, int total, int absent) {
        this.id = id;
        this.time = time;
        this.baseclass = baseclass;
        this.room = room;
        this.total = total;
        this.absent = absent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBaseclass() {
        return baseclass;
    }

    public void setBaseclass(String baseclass) {
        this.baseclass = baseclass;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }
}
