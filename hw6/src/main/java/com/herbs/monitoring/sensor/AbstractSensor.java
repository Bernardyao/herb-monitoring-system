package com.herbs.monitoring.sensor;

public abstract class AbstractSensor {
    protected String sensorId;
    protected String sensorType;

    public AbstractSensor(String sensorId, String sensorType) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
    }

    public abstract void readData();

    public String getSensorId() {
        return sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void displaySensorInfo() {
        System.out.println("Sensor ID: " + sensorId);
        System.out.println("Sensor Type: " + sensorType);
    }
}