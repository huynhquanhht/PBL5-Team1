package com.example.plb.model;

public class Student {

    private String id;

    private String name;

    private String baseClass;

    private boolean status;

    public Student(String id, String name, String baseClass, boolean status) {
        this.id = id;
        this.name = name;
        this.baseClass = baseClass;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }
}
