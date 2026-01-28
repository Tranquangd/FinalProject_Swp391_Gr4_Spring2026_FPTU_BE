@echo off
echo ============================================
echo Insert Sample Data to SQL Server Database
echo ============================================
echo.

REM Check if sqlcmd is available
where sqlcmd >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: sqlcmd is not found in PATH!
    echo Please install SQL Server Command Line Utilities or use SQL Server Management Studio
    echo.
    echo Alternative: Open SQL Server Management Studio and run:
    echo   demo\src\main\resources\database\insert-sample-data.sql
    pause
    exit /b 1
)

echo Connecting to SQL Server...
echo Server: localhost
echo Database: SWP391_Tool
echo Username: sa
echo.

REM Run the insert script
sqlcmd -S localhost -U sa -P 123 -d SWP391_Tool -i "src\main\resources\database\insert-sample-data.sql"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ============================================
    echo SUCCESS: Sample data inserted!
    echo ============================================
    echo.
    echo You can now test the API:
    echo   http://localhost:8080/api/users
) else (
    echo.
    echo ============================================
    echo ERROR: Failed to insert data
    echo ============================================
    echo.
    echo Please check:
    echo 1. SQL Server is running
    echo 2. Database SWP391_Tool exists
    echo 3. Tables are created (run create-database.sql first)
    echo 4. Username/Password is correct
)

pause
