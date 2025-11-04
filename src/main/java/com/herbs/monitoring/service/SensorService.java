package com.herbs.monitoring.service;

import com.herbs.monitoring.dao.SensorDao;
import com.herbs.monitoring.model.SensorRecord;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service façade for sensor table CRUD operations.
 */
public class SensorService {
    private final SensorDao sensorDao;

    public SensorService() {
        this(new SensorDao());
    }

    public SensorService(SensorDao sensorDao) {
        this.sensorDao = Objects.requireNonNull(sensorDao);
    }

    public List<SensorRecord> listSensors() {
        return sensorDao.findAll();
    }

    public void addSensor(SensorRecord record) {
        sensorDao.insert(record);
    }

    public void updateSensor(SensorRecord record) {
        sensorDao.update(record);
    }

    public void deleteSensor(String sensorId) {
        sensorDao.delete(sensorId);
    }

    public Optional<SensorRecord> findSensor(String sensorId) {
        return sensorDao.findById(sensorId);
    }
}
