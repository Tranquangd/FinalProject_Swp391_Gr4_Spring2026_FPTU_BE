@echo off
echo ========================================
echo Entity Generator - Generate JPA Entities
echo ========================================
echo.

REM Check if database exists and is accessible
echo Checking database connection...
echo.

REM Compile EntityGenerator
echo Compiling EntityGenerator...
javac -cp "target/classes;%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\*\*.jar" src\main\java\com\example\demo\util\EntityGenerator.java -d target/classes 2>nul

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Cannot compile. Please run Maven build first:
    echo   .\mvnw.cmd clean compile
    echo.
    echo Or run from IntelliJ IDEA:
    echo   1. Open EntityGenerator.java
    echo   2. Right-click -^> Run 'EntityGenerator.main()'
    echo.
    pause
    exit /b 1
)

REM Run EntityGenerator
echo Running EntityGenerator...
echo.
java -cp "target/classes;%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\*\*.jar" com.example.demo.util.EntityGenerator

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo SUCCESS! Entities generated in:
    echo src\main\java\com\example\demo\entity\
    echo ========================================
) else (
    echo.
    echo ========================================
    echo ERROR: Failed to generate entities
    echo ========================================
    echo.
    echo Please check:
    echo   1. SQL Server is running
    echo   2. Database SWP391_Tool exists
    echo   3. Connection settings in EntityGenerator.java
    echo.
)

pause
