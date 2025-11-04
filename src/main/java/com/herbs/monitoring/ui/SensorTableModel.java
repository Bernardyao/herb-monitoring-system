package com.herbs.monitoring.ui;

import com.herbs.monitoring.model.SensorRecord;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model that displays sensors inside the main window.
 */
public class SensorTableModel extends AbstractTableModel {
    private static final String[] HEADERS = {"sensor_id", "sensor_type", "location", "protocol"};
    private final List<SensorRecord> sensors = new ArrayList<>();

    public void setSensors(List<SensorRecord> data) {
        sensors.clear();
        sensors.addAll(data);
        fireTableDataChanged();
    }

    public SensorRecord getSensorAt(int row) {
        if (row < 0 || row >= sensors.size()) {
            return null;
        }
        return sensors.get(row);
    }

    @Override
    public int getRowCount() {
        return sensors.size();
    }

    @Override
    public int getColumnCount() {
        return HEADERS.length;
    }

    @Override
    public String getColumnName(int column) {
        return HEADERS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SensorRecord record = sensors.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> record.getSensorId();
            case 1 -> record.getSensorType();
            case 2 -> record.getLocation();
            case 3 -> record.getProtocol();
            default -> "";
        };
    }
}
