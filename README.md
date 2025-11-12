# 中药种植监控系统 - 快速开始 / Quick Start

## 环境要求 / Prerequisites
- JDK 17 或更高版本
- Maven 3.6+（建议用于构建和打包，Windows PowerShell 下运行）

## GitHub 仓库
- 仓库地址: https://github.com/Bernardyao/herb-monitoring-system

## 项目结构 / Project Structure
```
hw6/
├── src/main/java/com/herbs/monitoring/
│   ├── AppLauncher.java
│   ├── dao/
│   │   ├── DBUtil.java
│   │   ├── SensorDao.java
│   │   └── SensorDataDao.java
│   ├── model/
│   │   ├── SensorRecord.java
│   │   └── SensorDataRecord.java
│   ├── sensor/
│   │   ├── AbstractSensor.java
│   │   ├── TemperatureSensor.java
│   │   ├── HumiditySensor.java
│   │   ├── SensorFactory.java
│   │   ├── SensorDataCallback.java
│   │   ├── generator/
│   │   │   ├── RunnableSensorGenerator.java
│   │   │   └── ThreadSensorGenerator.java
│   │   └── model/
│   │       └── SensorReading.java
│   ├── service/
│   │   ├── SensorService.java
│   │   └── ThresholdService.java
│   └── ui/
│       ├── LoginFrame.java
│       ├── MainFrame.java
│       └── SensorTableModel.java
├── src/main/resources/
│   └── herb_threshold.txt
├── data/                       # 运行时数据库（herb-monitor.db）
├── target/                     # Maven 构建输出
├── pom.xml
├── README.md
└── 学习指南.md
```

## 使用说明 / How to build & run (PowerShell)

### 1) 使用 Maven 编译
在项目根目录（包含 `pom.xml`）运行：

```powershell
mvn clean compile
```

### 2) 使用 Maven 打包（生成包含依赖的可执行 JAR）
这会在 `target/` 下生成两个 JAR 文件，其中 `-jar-with-dependencies.jar` 包含所有依赖：

```powershell
mvn clean package
```

生成文件示例：
- `target/herb-monitoring-system-1.0.0.jar` (仅项目代码)
- `target/herb-monitoring-system-1.0.0-jar-with-dependencies.jar` (包含所有依赖，可直接运行)

### 3) 运行打包后的程序

```powershell
# 直接运行包含依赖的可执行 JAR
java -jar target\herb-monitoring-system-1.0.0-jar-with-dependencies.jar
```

### 4) 如果需要直接用 Maven 运行（不打包）

```powershell
mvn exec:java
```

**默认登录账号**: `admin` / `admin`

## 主要功能 / Main Features
- 登录界面 (LoginFrame)
- 传感器 CRUD（新增/修改/删除/刷新）
- 手动采集传感器数据
- 周期性采集与阈值告警

## 配置（阈值）
编辑 `src/main/resources/herb_threshold.txt`：

```properties
temperature=28  # 温度超过 28°C 时告警
humidity=75     # 湿度超过 75% 时告警
```

## 数据库
SQLite 数据库文件位于：`data/herb-monitor.db`（程序首次运行时会自动创建）

查看数据（示例）：

```powershell
# 在 Windows 上可以使用 sqlite3 工具查看
sqlite3 data\herb-monitor.db
SELECT * FROM sensor ORDER BY id LIMIT 10;
SELECT * FROM sensor_monitor ORDER BY collect_time DESC LIMIT 10;
```

## 常见问题排查（Troubleshooting）
- 若编译失败，确认 JDK 与 Maven 版本： `java -version`、`mvn -version`
- 若 UI 无法启动，查看控制台错误并确保主类为 `com.herbs.monitoring.AppLauncher`
- 如出现数据库错误，可删除 `data/` 目录重试（会重建数据库）

## Git & GitHub
- 我已将项目推送到： https://github.com/Bernardyao/herb-monitoring-system
- 常用 Git 命令：

```powershell
# 初始化（已完成）
git init
# 添加远程（若未设置）
# git remote add origin https://github.com/<your-user>/herb-monitoring-system.git
# 推送到 main 分支
git branch -M main
git push -u origin main
```

## 开发建议 / Next Steps
- 修改阈值文件观察告警行为
- 添加新的传感器类型（继承 `AbstractSensor`）
- 改进 UI 布局或加入图表展示

---

更多详细的学习教程请参阅：`学习指南.md`

Happy Learning! 🎓
