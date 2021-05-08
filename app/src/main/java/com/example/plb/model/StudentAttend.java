package com.example.plb.model;

public class StudentAttend {

    private String name;
    private String urlAvatar;
    private String baseclass;
    private String subject;
    private String room;
    private String id;
    private String timeattend;
    private String status;

    public StudentAttend(String name, String urlAvatar, String baseclass, String subject, String room, String id, String timeattend, String status) {
        this.name = name;
        this.urlAvatar = urlAvatar;
        this.baseclass = baseclass;
        this.subject = subject;
        this.room = room;
        this.id = id;
        this.timeattend = timeattend;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getBaseclass() {
        return baseclass;
    }

    public void setBaseclass(String baseclass) {
        this.baseclass = baseclass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeattend() {
        return timeattend;
    }

    public void setTimeattend(String timeattend) {
        this.timeattend = timeattend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
