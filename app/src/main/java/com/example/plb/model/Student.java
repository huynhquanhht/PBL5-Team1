package com.example.plb.model;

public class Student {

    private String id;

    private String name;

    private String baseClass;

    public Student(String id, String name, String baseClass) {
        this.id = id;
        this.name = name;
        this.baseClass = baseClass;
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
