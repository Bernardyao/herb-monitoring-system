@echo off
chcp 65001 > nul
REM Compile using javac directly

echo ===================================
echo Compiling Herb Monitoring System...
echo ===================================

REM Create directories
if not exist "target\classes" mkdir "target\classes"
if not exist "lib" mkdir "lib"

REM Download SQLite JDBC if needed
if not exist "lib\sqlite-jdbc-3.36.0.3.jar" (
    echo Downloading SQLite JDBC driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.36.0.3/sqlite-jdbc-3.36.0.3.jar' -OutFile 'lib\sqlite-jdbc-3.36.0.3.jar'"
)

REM Copy resources
xcopy /Y /Q "src\main\resources\*" "target\classes\" > nul 2>&1

REM Compile all Java files
echo Compiling Java source files...

javac -encoding UTF-8 -d target\classes -cp "lib\sqlite-jdbc-3.36.0.3.jar" ^
    src\main\java\com\herbs\monitoring\model\*.java ^
    src\main\java\com\herbs\monitoring\dao\*.java ^
    src\main\java\com\herbs\monitoring\sensor\model\*.java ^
    src\main\java\com\herbs\monitoring\sensor\SensorDataCallback.java ^
    src\main\java\com\herbs\monitoring\sensor\AbstractSensor.java ^
    src\main\java\com\herbs\monitoring\sensor\TemperatureSensor.java ^
    src\main\java\com\herbs\monitoring\sensor\HumiditySensor.java ^
    src\main\java\com\herbs\monitoring\sensor\SensorFactory.java ^
    src\main\java\com\herbs\monitoring\sensor\generator\*.java ^
    src\main\java\com\herbs\monitoring\service\*.java ^
    src\main\java\com\herbs\monitoring\ui\*.java ^
    src\main\java\com\herbs\monitoring\AppLauncher.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===================================
    echo Compilation Successful!
    echo ===================================
    echo.
    echo To run: run-java.bat
    echo.
) else (
    echo.
    echo ===================================
    echo Compilation Failed!
    echo ===================================
)

pause
