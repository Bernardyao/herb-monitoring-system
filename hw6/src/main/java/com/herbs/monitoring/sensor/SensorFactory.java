package com.herbs.monitoring.sensor;

import java.util.HashMap;
import java.util.Map;

public class SensorFactory {

    private static final Map<String, AbstractSensor> sensorCache = new HashMap<>();

    public static AbstractSensor createSensor(String type) {
        AbstractSensor sensor = sensorCache.get(type);
        if (sensor == null) {
            switch (type) {
                case "Temperature":
                    sensor = new TemperatureSensor();
                    break;
                case "Humidity":
                    sensor = new HumiditySensor();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown sensor type: " + type);
            }
            sensorCache.put(type, sensor);
        }
        return sensor;
    }
}