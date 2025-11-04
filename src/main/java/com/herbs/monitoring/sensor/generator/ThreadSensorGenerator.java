package com.herbs.monitoring.sensor.generator;

import com.herbs.monitoring.sensor.AbstractSensor;
import com.herbs.monitoring.sensor.SensorFactory;
import com.herbs.monitoring.sensor.SensorDataCallback;

import java.util.Locale;
import java.util.Optional;

/**
 * Demonstrates sensor batch creation using Thread inheritance.
 */
public class ThreadSensorGenerator extends Thread {
    private final String typeKey;
    private final String idPrefix;
    private final String location;
    private final int quantity;
    private final Optional<SensorDataCallback> callback;

    public ThreadSensorGenerator(String typeKey, String idPrefix, String location, int quantity) {
        this(typeKey, idPrefix, location, quantity, null);
    }

    public ThreadSensorGenerator(String typeKey, String idPrefix, String location, int quantity, SensorDataCallback callback) {
        super("ThreadSensorGenerator-" + idPrefix);
        this.typeKey = typeKey;
        this.idPrefix = idPrefix;
        this.location = location;
        this.quantity = quantity;
        this.callback = Optional.ofNullable(callback);
    }

    @Override
    public void run() {
        for (int i = 1; i <= quantity; i++) {
            String sensorId = String.format(Locale.CHINA, "%s-%03d", idPrefix, i);
            AbstractSensor sensor = SensorFactory.createByType(typeKey, sensorId, location);
            callback.ifPresent(sensor::addCallback);
            sensor.collect();
            sensor.listenData();
        }
    }
}
