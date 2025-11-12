package com.herbs.monitoring.service;

import com.herbs.monitoring.dao.SensorDao;
import com.herbs.monitoring.model.SensorRecord;

import java.util.List;

public class SensorService {
    private final SensorDao sensorDao;

    public SensorService(SensorDao sensorDao) {
        this.sensorDao = sensorDao;
    }

    public void addSensor(SensorRecord sensor) {
        sensorDao.insert(sensor);
    }

    public void updateSensor(SensorRecord sensor) {
        sensorDao.update(sensor);
    }

    public void deleteSensor(int sensorId) {
        sensorDao.delete(sensorId);
    }

    public SensorRecord getSensor(int sensorId) {
        return sensorDao.findById(sensorId);
    }

    public List<SensorRecord> getAllSensors() {
        return sensorDao.findAll();
    }
}