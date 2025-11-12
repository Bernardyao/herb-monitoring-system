package com.herbs.monitoring.ui;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import com.herbs.monitoring.model.SensorDataRecord;

public class SensorTableModel extends AbstractTableModel {
    private List<SensorDataRecord> sensorDataRecords;
    private final String[] columnNames = {"Sensor ID", "Temperature", "Humidity", "Timestamp"};

    public SensorTableModel(List<SensorDataRecord> sensorDataRecords) {
        this.sensorDataRecords = sensorDataRecords;
    }

    @Override
    public int getRowCount() {
        return sensorDataRecords.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SensorDataRecord record = sensorDataRecords.get(rowIndex);
        switch (columnIndex) {
            case 0: return record.getSensorId();
            case 1: return record.getTemperature();
            case 2: return record.getHumidity();
            case 3: return record.getTimestamp();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setSensorDataRecords(List<SensorDataRecord> sensorDataRecords) {
        this.sensorDataRecords = sensorDataRecords;
        fireTableDataChanged();
    }
}