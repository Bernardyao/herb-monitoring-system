package com.herbs.monitoring.dao;

import com.herbs.monitoring.model.SensorDataRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for persisted sensor readings.
 */
public class SensorDataDao {

    public void insert(SensorDataRecord record) {
        String sql = "INSERT INTO sensor_monitor(sensor_id, data, data_type, collect_time, protocol) VALUES(?,?,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, record.getSensorId());
            ps.setDouble(2, record.getDataValue());
            ps.setString(3, record.getDataType());
            ps.setString(4, record.getCollectTime().toString());
            ps.setString(5, record.getProtocol());
            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to write sensor monitor record", exception);
        }
    }

    public List<SensorDataRecord> findRecent(int limit) {
        String sql = "SELECT sensor_id, data, data_type, collect_time, protocol " +
                "FROM sensor_monitor ORDER BY collect_time DESC LIMIT ?";
        List<SensorDataRecord> records = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new SensorDataRecord(
                        rs.getString("sensor_id"),
                        rs.getDouble("data"),
                        rs.getString("data_type"),
                        LocalDateTime.parse(rs.getString("collect_time")),
                        rs.getString("protocol")
                ));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to load sensor monitor records", exception);
        }
        return records;
    }
}
