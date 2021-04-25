package com.example.plb.model;

public class Info {
    private String id;
    private String name;
    private String phone;
    private boolean sex;
    private String birthDay;
    private String url;

    public Info(String id, String name, String phone, boolean sex, String birthDay, String url) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.birthDay = birthDay;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
