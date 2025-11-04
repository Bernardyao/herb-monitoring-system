package com.herbs.monitoring.sensor;

import java.util.Random;

/**
 * Simulated humidity sensor that communicates via UDP.
 */
public class HumiditySensor extends AbstractSensor {
    private static final int MIN_HUM = 40;
    private static final int MAX_HUM = 80;

    public HumiditySensor(String sensorId, String location) {
        super(sensorId, location, "UDP", "湿度传感器");
    }

    @Override
    protected double collectData(Random random) {
        return MIN_HUM + random.nextInt((MAX_HUM - MIN_HUM) + 1);
    }

    @Override
    public String listenData() {
        return "UDP监听：湿度数据已返回";
    }

    @Override
    protected String dataTypeKey() {
        return "humidity";
    }
}
