package com.example.plb.model;

public class Schedule {

    private String id;
    private String subject;
    private String codeclass;
    private String timeStart;
    private String timeEnd;
    private String room;
    private String serial;
    private String total;
    private String idAccount;

    public Schedule(String id, String subject, String codeclass, String timeStart, String timeEnd, String room, String serial, String total, String idAccount) {
        this.id = id;
        this.subject = subject;
        this.codeclass = codeclass;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.room = room;
        this.serial = serial;
        this.total = total;
        this.idAccount = idAccount;
    }

    public String getCodeclass() {
        return codeclass;
    }

    public void setCodeclass(String codeclass) {
        this.codeclass = codeclass;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }
}
