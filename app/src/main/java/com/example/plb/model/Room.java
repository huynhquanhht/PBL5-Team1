package com.example.plb.model;

public class Room {

    private String building;
    private String serial;

    public Room(String building, String serial) {
        this.building = building;
        this.serial = serial;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
