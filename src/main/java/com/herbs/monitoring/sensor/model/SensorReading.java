package com.herbs.monitoring.sensor.model;

import java.time.LocalDateTime;

/**
 * Immutable value object describing a single sensor reading.
 */
public class SensorReading {
    private final String sensorId;
    private final String sensorType;
    private final String protocol;
    private final String location;
    private final String dataTypeKey;
    private final double value;
    private final LocalDateTime timestamp;

    public SensorReading(String sensorId, String sensorType, String protocol, String location, String dataTypeKey, double value, LocalDateTime timestamp) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.protocol = protocol;
        this.location = location;
        this.dataTypeKey = dataTypeKey;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getLocation() {
        return location;
    }

    public String getDataTypeKey() {
        return dataTypeKey;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
