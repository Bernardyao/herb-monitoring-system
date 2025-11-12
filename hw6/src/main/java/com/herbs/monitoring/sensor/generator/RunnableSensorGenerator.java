public class RunnableSensorGenerator implements Runnable {
    private final SensorDataCallback callback;
    private final int interval; // in milliseconds

    public RunnableSensorGenerator(SensorDataCallback callback, int interval) {
        this.callback = callback;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Simulate sensor data generation
                SensorReading reading = generateSensorReading();
                callback.onSensorDataReceived(reading);
                
                // Wait for the specified interval before generating the next reading
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit the loop if interrupted
            }
        }
    }

    private SensorReading generateSensorReading() {
        // Generate random sensor data (for demonstration purposes)
        double value = Math.random() * 100; // Simulated sensor value
        return new SensorReading(value);
    }
}