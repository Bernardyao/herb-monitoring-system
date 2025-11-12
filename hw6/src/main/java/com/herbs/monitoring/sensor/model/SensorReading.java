package com.herbs.monitoring.sensor.model;

public class SensorReading {
    private double value;
    private long timestamp;

    public SensorReading(double value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}