package com.herbs.monitoring.service;

import com.herbs.monitoring.dao.SensorDao;
import com.herbs.monitoring.model.SensorRecord;

public class ThresholdService {
    private final SensorDao sensorDao;
    private final double temperatureThreshold;
    private final double humidityThreshold;

    public ThresholdService(SensorDao sensorDao, double temperatureThreshold, double humidityThreshold) {
        this.sensorDao = sensorDao;
        this.temperatureThreshold = temperatureThreshold;
        this.humidityThreshold = humidityThreshold;
    }

    public boolean isTemperatureExceeded(SensorRecord record) {
        return record.getTemperature() > temperatureThreshold;
    }

    public boolean isHumidityExceeded(SensorRecord record) {
        return record.getHumidity() > humidityThreshold;
    }

    public void checkThresholds(SensorRecord record) {
        if (isTemperatureExceeded(record)) {
            alert("Temperature exceeded: " + record.getTemperature());
        }
        if (isHumidityExceeded(record)) {
            alert("Humidity exceeded: " + record.getHumidity());
        }
    }

    private void alert(String message) {
        // Implement alert mechanism (e.g., logging, notifications)
        System.out.println("ALERT: " + message);
    }
}