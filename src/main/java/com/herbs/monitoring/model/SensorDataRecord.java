package com.herbs.monitoring.model;

import java.time.LocalDateTime;

/**
 * Represents a persisted sensor data reading.
 */
public class SensorDataRecord {
    private final String sensorId;
    private final double dataValue;
    private final String dataType;
    private final LocalDateTime collectTime;
    private final String protocol;

    public SensorDataRecord(String sensorId, double dataValue, String dataType, LocalDateTime collectTime, String protocol) {
        this.sensorId = sensorId;
        this.dataValue = dataValue;
        this.dataType = dataType;
        this.collectTime = collectTime;
        this.protocol = protocol;
    }

    public String getSensorId() {
        return sensorId;
    }

    public double getDataValue() {
        return dataValue;
    }

    public String getDataType() {
        return dataType;
    }

    public LocalDateTime getCollectTime() {
        return collectTime;
    }

    public String getProtocol() {
        return protocol;
    }
}
