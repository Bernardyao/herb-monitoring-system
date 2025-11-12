package com.herbs.monitoring.sensor;

import com.herbs.monitoring.sensor.model.SensorReading;

public class TemperatureSensor extends AbstractSensor {
    private double currentTemperature;

    public TemperatureSensor() {
        super();
    }

    @Override
    public void readData() {
        // Simulate reading temperature data
        currentTemperature = generateTemperature();
        notifyDataListeners(new SensorReading(currentTemperature));
    }

    private double generateTemperature() {
        // Simulate temperature generation logic
        return 20 + Math.random() * 15; // Generates a temperature between 20 and 35
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }
}