package com.herbs.monitoring.sensor;

import java.util.Random;

/**
 * Simulated temperature sensor that communicates via TCP.
 */
public class TemperatureSensor extends AbstractSensor {
    private static final int MIN_TEMP = 15;
    private static final int MAX_TEMP = 35;

    public TemperatureSensor(String sensorId, String location) {
        super(sensorId, location, "TCP", "温度传感器");
    }

    @Override
    protected double collectData(Random random) {
        return MIN_TEMP + random.nextInt((MAX_TEMP - MIN_TEMP) + 1);
    }

    @Override
    public String listenData() {
        return "TCP监听：温度数据已返回";
    }

    @Override
    protected String dataTypeKey() {
        return "temperature";
    }
}
