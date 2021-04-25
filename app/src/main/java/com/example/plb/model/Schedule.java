package com.example.plb.model;

public class Schedule {

    private String id;
    private String subject;
    private String timeStart;
    private String timeEnd;
    private String room;
    private String idAccount;

    public Schedule(String id, String subject, String timeStart, String timeEnd, String room, String idAccount) {
        this.id = id;
        this.subject = subject;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.room = room;
        this.idAccount = idAccount;
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
