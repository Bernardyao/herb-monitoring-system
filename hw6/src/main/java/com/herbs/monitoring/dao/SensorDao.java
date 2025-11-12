package com.herbs.monitoring.dao;

import com.herbs.monitoring.model.SensorRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SensorDao {

    private Connection connection;

    public SensorDao(Connection connection) {
        this.connection = connection;
    }

    public void createSensor(SensorRecord sensor) throws SQLException {
        String sql = "INSERT INTO sensors (name, type, location) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sensor.getName());
            pstmt.setString(2, sensor.getType());
            pstmt.setString(3, sensor.getLocation());
            pstmt.executeUpdate();
        }
    }

    public SensorRecord readSensor(int id) throws SQLException {
        String sql = "SELECT * FROM sensors WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new SensorRecord(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getString("location"));
            }
        }
        return null;
    }

    public List<SensorRecord> readAllSensors() throws SQLException {
        List<SensorRecord> sensors = new ArrayList<>();
        String sql = "SELECT * FROM sensors";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sensors.add(new SensorRecord(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getString("location")));
            }
        }
        return sensors;
    }

    public void updateSensor(SensorRecord sensor) throws SQLException {
        String sql = "UPDATE sensors SET name = ?, type = ?, location = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sensor.getName());
            pstmt.setString(2, sensor.getType());
            pstmt.setString(3, sensor.getLocation());
            pstmt.setInt(4, sensor.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteSensor(int id) throws SQLException {
        String sql = "DELETE FROM sensors WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}