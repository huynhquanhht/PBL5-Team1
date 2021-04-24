package com.example.plb.model;

public class ClassRoom {

    private String idClass;

    private String name;

    public ClassRoom(String idClass, String name) {
        this.idClass = idClass;
        this.name = name;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
