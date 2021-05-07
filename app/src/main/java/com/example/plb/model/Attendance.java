package com.example.plb.model;

public class Attendance {
    private String id;
    private String timeattend;
    private String idschedule;
    private String absent;
    private String total;

    public Attendance(String id, String timeattend, String idschedule, String absent, String total) {
        this.id = id;
        this.timeattend = timeattend;
        this.idschedule = idschedule;
        this.absent = absent;
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getIdschedule() {
        return idschedule;
    }

    public void setIdschedule(String idschedule) {
        this.idschedule = idschedule;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }
}
