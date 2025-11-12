package com.herbs.monitoring.sensor.generator;

import com.herbs.monitoring.sensor.SensorDataCallback;
import com.herbs.monitoring.sensor.model.SensorReading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadSensorGenerator {
    private final SensorDataCallback callback;
    private final ExecutorService executorService;
    private final int numberOfSensors;

    public ThreadSensorGenerator(SensorDataCallback callback, int numberOfSensors) {
        this.callback = callback;
        this.numberOfSensors = numberOfSensors;
        this.executorService = Executors.newFixedThreadPool(numberOfSensors);
    }

    public void startGenerating() {
        for (int i = 0; i < numberOfSensors; i++) {
            final int sensorId = i;
            executorService.submit(() -> {
                while (true) {
                    SensorReading reading = generateSensorReading(sensorId);
                    callback.onDataReceived(reading);
                    try {
                        TimeUnit.SECONDS.sleep(1); // Simulate delay between readings
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

    private SensorReading generateSensorReading(int sensorId) {
        // Simulate generating sensor reading
        double value = Math.random() * 100; // Example: random value between 0 and 100
        return new SensorReading(sensorId, value);
    }

    public void stopGenerating() {
        executorService.shutdownNow();
    }
}