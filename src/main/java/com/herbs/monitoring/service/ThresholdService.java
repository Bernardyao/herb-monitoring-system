package com.herbs.monitoring.service;

import com.herbs.monitoring.sensor.model.SensorReading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Loads threshold configuration and evaluates sensor readings for alerts.
 */
public class ThresholdService {
    private static final String RESOURCE_NAME = "herb_threshold.txt";
    private final Map<String, Double> thresholds = new HashMap<>();
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public ThresholdService() {
        loadThresholds();
    }

    private void loadThresholds() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_NAME)) {
            if (inputStream == null) {
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                        .forEach(line -> {
                            String[] parts = line.split("=");
                            if (parts.length == 2) {
                                String key = parts[0].trim().toLowerCase(Locale.ROOT);
                                Double value = Double.valueOf(parts[1].trim());
                                thresholds.put(key, value);
                            }
                        });
            }
        } catch (IOException ignore) {
            // Not critical: default to no thresholds if file missing.
        }
    }

    public Optional<String> evaluate(SensorReading reading) {
        String dataKey = reading.getDataTypeKey().toLowerCase(Locale.ROOT);
        Double limit = thresholds.get(dataKey);
        if (limit == null) {
            limit = thresholds.get(reading.getSensorType().toLowerCase(Locale.ROOT));
        }
        if (limit != null && reading.getValue() > limit) {
            String message = String.format("%s %s 当前值 %s 超过阈值 %s (协议:%s)",
                    reading.getSensorType(),
                    reading.getSensorId(),
                    decimalFormat.format(reading.getValue()),
                    decimalFormat.format(limit),
                    reading.getProtocol());
            return Optional.of(message);
        }
        return Optional.empty();
    }
}
