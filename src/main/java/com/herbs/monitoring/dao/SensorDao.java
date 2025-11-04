package com.herbs.monitoring.dao;

import com.herbs.monitoring.model.SensorRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for CRUD operations on the sensor table.
 */
public class SensorDao {

    public List<SensorRecord> findAll() {
        String sql = "SELECT sensor_id, sensor_type, location, protocol FROM sensor ORDER BY sensor_id";
        List<SensorRecord> records = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                records.add(new SensorRecord(
                        rs.getString("sensor_id"),
                        rs.getString("sensor_type"),
                        rs.getString("location"),
                        rs.getString("protocol")
                ));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to load sensor data", exception);
        }
        return records;
    }

    public Optional<SensorRecord> findById(String sensorId) {
        String sql = "SELECT sensor_id, sensor_type, location, protocol FROM sensor WHERE sensor_id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sensorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new SensorRecord(
                        rs.getString("sensor_id"),
                        rs.getString("sensor_type"),
                        rs.getString("location"),
                        rs.getString("protocol")
                ));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to query sensor", exception);
        }
        return Optional.empty();
    }

    public void insert(SensorRecord record) {
        String sql = "INSERT INTO sensor(sensor_id, sensor_type, location, protocol) VALUES (?,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, record.getSensorId());
            ps.setString(2, record.getSensorType());
            ps.setString(3, record.getLocation());
            ps.setString(4, record.getProtocol());
            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to insert sensor", exception);
        }
    }

    public void update(SensorRecord record) {
        String sql = "UPDATE sensor SET sensor_type = ?, location = ?, protocol = ? WHERE sensor_id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, record.getSensorType());
            ps.setString(2, record.getLocation());
            ps.setString(3, record.getProtocol());
            ps.setString(4, record.getSensorId());
            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to update sensor", exception);
        }
    }

    public void delete(String sensorId) {
        String sql = "DELETE FROM sensor WHERE sensor_id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sensorId);
            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to delete sensor", exception);
        }
    }
}
