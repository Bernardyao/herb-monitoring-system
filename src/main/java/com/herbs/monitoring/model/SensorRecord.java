package com.herbs.monitoring.model;

import java.util.Objects;

/**
 * Represents a sensor row from the sensor table.
 */
public class SensorRecord {
    private final String sensorId;
    private final String sensorType;
    private final String location;
    private final String protocol;

    public SensorRecord(String sensorId, String sensorType, String location, String protocol) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.location = location;
        this.protocol = protocol;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getLocation() {
        return location;
    }

    public String getProtocol() {
        return protocol;
    }

    public SensorRecord withType(String newType) {
        return new SensorRecord(sensorId, newType, location, protocol);
    }

    public SensorRecord withLocation(String newLocation) {
        return new SensorRecord(sensorId, sensorType, newLocation, protocol);
    }

    public SensorRecord withProtocol(String newProtocol) {
        return new SensorRecord(sensorId, sensorType, location, newProtocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorRecord that = (SensorRecord) o;
        return Objects.equals(sensorId, that.sensorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId);
    }
}
