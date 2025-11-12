package com.herbs.monitoring.sensor;

public interface SensorDataCallback {
    void onDataReceived(SensorReading reading);
    void onError(String errorMessage);
}