# 中药种植监控系统 - 项目说明

## 项目简介
中药种植监控系统是一个用于监控中药种植环境的应用程序。该系统通过传感器收集温度和湿度数据，并提供实时监控和告警功能。用户可以通过图形用户界面（GUI）进行数据查看和管理。

## 技术栈
- Java
- Swing（图形用户界面）
- SQLite（数据库）
- Maven（项目管理和构建工具）

## 项目结构
```
hw6/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── herbs/
│   │   │           └── monitoring/
│   │   │               ├── AppLauncher.java
│   │   │               ├── dao/
│   │   │               │   ├── DBUtil.java
│   │   │               │   ├── SensorDao.java
│   │   │               │   └── SensorDataDao.java
│   │   │               ├── model/
│   │   │               │   ├── SensorRecord.java
│   │   │               │   └── SensorDataRecord.java
│   │   │               ├── sensor/
│   │   │               │   ├── AbstractSensor.java
│   │   │               │   ├── TemperatureSensor.java
│   │   │               │   ├── HumiditySensor.java
│   │   │               │   ├── SensorFactory.java
│   │   │               │   ├── SensorDataCallback.java
│   │   │               │   ├── model/
│   │   │               │   │   └── SensorReading.java
│   │   │               │   └── generator/
│   │   │               │       ├── ThreadSensorGenerator.java
│   │   │               │       └── RunnableSensorGenerator.java
│   │   │               ├── service/
│   │   │               │   ├── SensorService.java
│   │   │               │   └── ThresholdService.java
│   │   │               └── ui/
│   │   │                   ├── LoginFrame.java
│   │   │                   ├── MainFrame.java
│   │   │                   └── SensorTableModel.java
│   │   └── resources/
│   │       └── herb_threshold.txt
│   └── test/
│       └── java/
│           └── com/
│               └── herbs/
│                   └── monitoring/
├── target/
├── data/
├── pom.xml
├── README.md
└── 学习指南.md
```

## 使用说明

### 环境要求
- JDK 1.8 或更高版本
- Maven 3.6 或更高版本

### 编译与运行
1. **编译项目**：
   在项目根目录下运行以下命令：
   ```
   mvn clean package
   ```

2. **运行程序**：
   编译完成后，使用以下命令运行主程序：
   ```
   java -cp target/hw6-1.0-SNAPSHOT.jar com.herbs.monitoring.AppLauncher
   ```

### 默认登录信息
- 用户名：`admin`
- 密码：`admin`

## 贡献
欢迎任何形式的贡献！请提交问题或拉取请求。

## 许可证
本项目采用 MIT 许可证，详情请参阅 LICENSE 文件。

## 联系
如有任何问题，请联系项目维护者。