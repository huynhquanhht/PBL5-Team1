package com.example.plb.model;

public class Account {
    private String id;
    private String user;
    private String pass;
    private String idInfo;

    public Account(String id, String user, String pass, String idInfo) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.idInfo = idInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(String idInfo) {
        this.idInfo = idInfo;
    }
}
