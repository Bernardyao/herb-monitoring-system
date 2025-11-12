package com.herbs.monitoring.model;

public class SensorRecord {
    private int id;
    private String sensorType;
    private String location;
    private String status;

    public SensorRecord(int id, String sensorType, String location, String status) {
        this.id = id;
        this.sensorType = sensorType;
        this.location = location;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}