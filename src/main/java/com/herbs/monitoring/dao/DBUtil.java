package com.herbs.monitoring.dao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility responsible for managing the embedded SQLite database used by the experiment.
 */
public final class DBUtil {
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path DB_PATH = DATA_DIR.resolve("herb-monitor.db");
    private static final String JDBC_URL = "jdbc:sqlite:" + DB_PATH.toAbsolutePath();

    private DBUtil() {
    }

    /**
     * Prepares the local database file and ensures tables plus seed data exist.
     */
    public static void initializeDatabase() {
        try {
            // Explicitly load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            if (Files.notExists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }
            ensureSchema();
            seedSensors();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to initialize database", ex);
        }
    }

    private static void ensureSchema() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS sensor (" +
                    "sensor_id TEXT PRIMARY KEY, " +
                    "sensor_type TEXT NOT NULL, " +
                    "location TEXT NOT NULL, " +
                    "protocol TEXT NOT NULL"
                    + ")");

            statement.execute("CREATE TABLE IF NOT EXISTS sensor_monitor (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sensor_id TEXT NOT NULL, " +
                    "data REAL NOT NULL, " +
                    "data_type TEXT NOT NULL, " +
                    "collect_time TEXT NOT NULL, " +
                    "protocol TEXT NOT NULL"
                    + ")");
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Schema initialization failure", exception);
        }
    }

    private static void seedSensors() {
        String checkSql = "SELECT COUNT(1) FROM sensor";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkSql)) {
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                insertSensor("TEMP-001", "温度传感器", "黄芪一区", "TCP");
                insertSensor("HUM-001", "湿度传感器", "黄芪二区", "UDP");
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, "Failed to seed sensors", exception);
        }
    }

    private static void insertSensor(String id, String type, String location, String protocol) throws SQLException {
        String sql = "INSERT INTO sensor(sensor_id, sensor_type, location, protocol) VALUES(?,?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, type);
            ps.setString(3, location);
            ps.setString(4, protocol);
            ps.executeUpdate();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    /**
     * Persists a sensor reading into the sensor_monitor table.
     */
    public static void persistSensorReading(String sensorId, double value, String dataType, String protocol) {
        String sql = "INSERT INTO sensor_monitor(sensor_id, data, data_type, collect_time, protocol) VALUES(?,?,?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sensorId);
            ps.setDouble(2, value);
            ps.setString(3, dataType);
            ps.setString(4, LocalDateTime.now().toString());
            ps.setString(5, protocol);
            ps.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Failed to persist sensor reading", exception);
        }
    }
}
