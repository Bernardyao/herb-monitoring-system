package com.herbs.monitoring.sensor;

import com.herbs.monitoring.sensor.model.SensorReading;

public class HumiditySensor extends AbstractSensor {
    private double humidity;

    public HumiditySensor() {
        super();
    }

    @Override
    public void readSensorData() {
        // Simulate reading humidity data
        this.humidity = generateHumidityReading();
        notifyDataCallback(new SensorReading(humidity));
    }

    private double generateHumidityReading() {
        // Simulate humidity reading logic (e.g., random value for demonstration)
        return Math.random() * 100; // Humidity percentage
    }

    public double getHumidity() {
        return humidity;
    }
}