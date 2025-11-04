@echo off
chcp 65001 > nul
REM Run the application using java directly

echo ===================================
echo Starting Herb Monitoring System...
echo ===================================
echo.
echo Login Credentials:
echo   Username: admin
echo   Password: admin
echo.
echo ===================================
echo.

java "-Dfile.encoding=UTF-8" -cp "target\classes;lib\sqlite-jdbc-3.36.0.3.jar" com.herbs.monitoring.AppLauncher

pause
