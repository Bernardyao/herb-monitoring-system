package com.herbs.monitoring.sensor.generator;

import com.herbs.monitoring.sensor.AbstractSensor;
import com.herbs.monitoring.sensor.SensorDataCallback;
import com.herbs.monitoring.sensor.SensorFactory;

import java.util.Locale;
import java.util.Optional;

/**
 * Demonstrates sensor batch creation using Runnable implementation.
 */
public class RunnableSensorGenerator implements Runnable {
    private final String typeKey;
    private final String idPrefix;
    private final String location;
    private final int quantity;
    private final Optional<SensorDataCallback> callback;

    public RunnableSensorGenerator(String typeKey, String idPrefix, String location, int quantity) {
        this(typeKey, idPrefix, location, quantity, null);
    }

    public RunnableSensorGenerator(String typeKey, String idPrefix, String location, int quantity, SensorDataCallback callback) {
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
            Thread worker = new Thread(() -> {
                sensor.collect();
                sensor.listenData();
            }, "SensorWorker-" + sensorId);
            worker.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
