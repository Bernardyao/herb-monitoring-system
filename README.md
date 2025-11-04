# 中药种植监控系统 - Quick Start

## 环境要求 / Prerequisites
- JDK 17 或更高版本
- 无需 Maven（使用 javac 直接编译）

## 项目结构 / Project Structure
```
hw6/
├── src/main/java/              # Java source code
│   └── com/herbs/monitoring/
│       ├── AppLauncher.java    # Main entry point
│       ├── dao/                # Database access layer
│       ├── model/              # Data models
│       ├── sensor/             # Sensor simulation
│       ├── service/            # Business logic
│       └── ui/                 # Swing UI components
├── src/main/resources/         # Configuration files
│   └── herb_threshold.txt      # Alert thresholds
├── lib/                        # External libraries
├── data/                       # Database files
├── compile.bat                 # Compile script
└── run.bat                     # Run main application
```

## 快速开始 / Quick Start

### 1. 编译项目 / Compile
```bash
compile.bat
```

### 2. 运行程序 / Run Application
```bash
run.bat
```

**Login Credentials:**
- Username: `admin`
- Password: `admin`

## 主要功能 / Main Features

### Feature 1: Login Interface (Experiment 1)
- Enter username and password
- Click "Login" button
- Observe window transition to main interface

### Feature 2: CRUD Operations (Experiment 3)
- **Add**: Click "新增" to add new sensor
- **Edit**: Select a row, click "修改" to modify
- **Delete**: Select a row, click "删除" to remove
- **Refresh**: Click "刷新" to reload table

### Feature 3: Manual Data Collection (Experiment 4)
1. Select sensor type from dropdown (Temperature/Humidity)
2. Enter sensor ID (e.g., TEMP-001)
3. Click "采集传感器数据"
4. View popup with collected data
5. Check database to verify data persistence

### Feature 4: Periodic Collection & Alerts (Experiment 5)
1. Select sensor type and ID
2. Click "启动周期采集" to start (5-second interval)
3. System automatically collects data
4. When threshold exceeded, alert popup appears
5. Click "停止周期采集" to stop

## Key Learning Points

### 1. Abstract Class Pattern (AbstractSensor)
```java
// Template method defines the process
public SensorReading collect() {
    double value = collectData(random);  // Subclass implements
    // Store to database
    // Notify callbacks
    return reading;
}
```

### 2. Thread vs Runnable
- **Thread**: `class Generator extends Thread`
- **Runnable**: `class Generator implements Runnable`
- Runnable is more flexible (can extend other classes)

### 3. Swing UI Threading
```java
// Long task in background thread
executor.submit(() -> {
    sensor.collect();
    // Update UI on EDT
    SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(...);
    });
});
```

### 4. Timer for Periodic Tasks
```java
Timer timer = new Timer(true);  // daemon thread
timer.scheduleAtFixedRate(task, 0, 5000);  // every 5 seconds
```

## Database

SQLite database automatically created at: `data/herb-monitor.db`

**Tables:**
- `sensor`: Sensor metadata (id, type, location, protocol)
- `sensor_monitor`: Collected data (sensor_id, value, timestamp)

**View data:**
```bash
sqlite3 data/herb-monitor.db
SELECT * FROM sensor;
SELECT * FROM sensor_monitor ORDER BY collect_time DESC LIMIT 10;
```

## Configuration

Edit `src/main/resources/herb_threshold.txt`:
```properties
temperature=28  # Alert when temperature > 28°C
humidity=75     # Alert when humidity > 75%
```

## Troubleshooting

**Q: Compilation fails?**
- Check Java version: `java -version` (should be 17+)
- Check Maven: `mvn -version`
- Clean build: `mvn clean compile`

**Q: UI doesn't appear?**
- Ensure you're running `AppLauncher` as main class
- Check console for error messages

**Q: Database errors?**
- Delete `data/` folder to reset
- Restart application

**Q: Chinese characters display incorrectly?**
- Maven handles encoding automatically
- POM configured with UTF-8

## Next Steps

1. **Modify Thresholds**: Change values in `herb_threshold.txt`
2. **Add New Sensor Type**: Extend `AbstractSensor`
3. **Improve UI**: Customize `MainFrame.java`
4. **Add Charts**: Integrate data visualization library

## Project Architecture

```
┌─────────────┐
│ LoginFrame  │ Login UI
└──────┬──────┘
       │ success
       ▼
┌─────────────┐
│  MainFrame  │ Main UI (CRUD + Collection)
└──────┬──────┘
       │ uses
       ▼
┌──────────────┐      ┌────────────────┐
│SensorService │◄─────│ AbstractSensor │ Sensor Simulation
└──────┬───────┘      │  - Temperature │
       │              │  - Humidity    │
       ▼              └────────────────┘
┌──────────────┐
│  SensorDao   │ Database Access
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   DBUtil     │ SQLite Connection
└──────────────┘
```

## Experiment Checklist

- [ ] Experiment 1: Login UI works
- [ ] Experiment 2: CRUD operations complete
- [ ] Experiment 3: Manual collection works
- [ ] Experiment 4: Periodic collection + alerts work

---

Happy Learning! 🎓
For detailed Chinese guide, see: 学习指南.md
