package com.herbs.monitoring.sensor;

import com.herbs.monitoring.dao.DBUtil;
import com.herbs.monitoring.sensor.model.SensorReading;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Base class that encapsulates common behavior across simulated sensors.
 */
public abstract class AbstractSensor {
    private final String sensorId;
    private final String location;
    private final String protocol;
    private final String sensorTypeLabel;
    private final Random random = new Random();
    private final CopyOnWriteArrayList<SensorDataCallback> callbacks = new CopyOnWriteArrayList<>();

    protected AbstractSensor(String sensorId, String location, String protocol, String sensorTypeLabel) {
        this.sensorId = Objects.requireNonNull(sensorId, "sensorId");
        this.location = Objects.requireNonNull(location, "location");
        this.protocol = Objects.requireNonNull(protocol, "protocol");
        this.sensorTypeLabel = Objects.requireNonNull(sensorTypeLabel, "sensorTypeLabel");
    }

    /**
     * Template method: collects a data point, writes it to DB, notifies listeners, and returns the reading.
     */
    public SensorReading collect() {
        double value = collectData(random);
    SensorReading reading = new SensorReading(sensorId, sensorTypeLabel, protocol, location, dataTypeKey(), value, LocalDateTime.now());
        DBUtil.persistSensorReading(sensorId, value, dataTypeKey(), protocol);
        callbacks.forEach(callback -> callback.onReading(reading));
        return reading;
    }

    /**
     * Adds a listener that will be notified after each data point is collected.
     */
    public void addCallback(SensorDataCallback callback) {
        callbacks.add(Objects.requireNonNull(callback));
    }

    /**
     * Removes a listener.
     */
    public void removeCallback(SensorDataCallback callback) {
        callbacks.remove(callback);
    }

    /**
     * Simulates the network interaction specific to the sensor type.
     */
    public abstract String listenData();

    /**
     * Implemented by subclasses to provide simulated data generation.
     */
    protected abstract double collectData(Random random);

    /**
     * Provides the data type key used when persisting to the database.
     */
    protected abstract String dataTypeKey();

    public String getSensorId() {
        return sensorId;
    }

    public String getLocation() {
        return location;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSensorTypeLabel() {
        return sensorTypeLabel;
    }
}
