package com.herbs.monitoring.sensor;

/**
 * Central place to create sensors based on type key.
 */
public final class SensorFactory {
    private SensorFactory() {
    }

    public static AbstractSensor createTemperature(String sensorId, String location) {
        return new TemperatureSensor(sensorId, location);
    }

    public static AbstractSensor createHumidity(String sensorId, String location) {
        return new HumiditySensor(sensorId, location);
    }

    public static AbstractSensor createByType(String typeKey, String sensorId, String location) {
        return switch (typeKey) {
            case "温度传感器/TCP", "temperature", "温度传感器" -> createTemperature(sensorId, location);
            case "湿度传感器/UDP", "humidity", "湿度传感器" -> createHumidity(sensorId, location);
            default -> throw new IllegalArgumentException("未知传感器类型: " + typeKey);
        };
    }
}
