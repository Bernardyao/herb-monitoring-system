package com.herbs.monitoring.sensor;

import com.herbs.monitoring.sensor.model.SensorReading;

/**
 * Callback invoked when a sensor finishes collecting a data point.
 */
@FunctionalInterface
public interface SensorDataCallback {
    void onReading(SensorReading reading);
}
