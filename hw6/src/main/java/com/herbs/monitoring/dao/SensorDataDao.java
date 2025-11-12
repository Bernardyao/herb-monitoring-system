package com.herbs.monitoring.dao;

import com.herbs.monitoring.model.SensorDataRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SensorDataDao {
    private Connection connection;

    public SensorDataDao(Connection connection) {
        this.connection = connection;
    }

    public void create(SensorDataRecord record) throws SQLException {
        String sql = "INSERT INTO sensor_data (sensor_id, value, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, record.getSensorId());
            pstmt.setDouble(2, record.getValue());
            pstmt.setTimestamp(3, Timestamp.valueOf(record.getTimestamp()));
            pstmt.executeUpdate();
        }
    }

    public SensorDataRecord read(int id) throws SQLException {
        String sql = "SELECT * FROM sensor_data WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new SensorDataRecord(rs.getInt("id"), rs.getInt("sensor_id"),
                        rs.getDouble("value"), rs.getTimestamp("timestamp").toLocalDateTime());
            }
        }
        return null;
    }

    public List<SensorDataRecord> readAll() throws SQLException {
        List<SensorDataRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM sensor_data";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                records.add(new SensorDataRecord(rs.getInt("id"), rs.getInt("sensor_id"),
                        rs.getDouble("value"), rs.getTimestamp("timestamp").toLocalDateTime()));
            }
        }
        return records;
    }

    public void update(SensorDataRecord record) throws SQLException {
        String sql = "UPDATE sensor_data SET sensor_id = ?, value = ?, timestamp = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, record.getSensorId());
            pstmt.setDouble(2, record.getValue());
            pstmt.setTimestamp(3, Timestamp.valueOf(record.getTimestamp()));
            pstmt.setInt(4, record.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sensor_data WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}