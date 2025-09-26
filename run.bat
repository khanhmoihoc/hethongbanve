@echo off
echo ========================================
echo    He Thong Ban Ve - Ticket System
echo ========================================
echo.

:: Lấy đường dẫn thư mục hiện tại
set "PROJECT_DIR=%~dp0"
cd /d "%PROJECT_DIR%"

:: Kiểm tra Java đã cài đặt chưa
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java khong duoc tim thay trong PATH!
    echo Vui long cai dat Java JDK hoac JRE va them vao PATH
    echo.
    pause
    exit /b 1
)

:: Hiển thị thông tin Java
echo [INFO] Tim thay Java:
java -version

echo.
echo [INFO] Dang bien dich cac file Java...
cd src
javac -cp . ui\*.java data\*.java model\*.java connection\*.java dao\*.java

if %errorlevel% neq 0 (
    echo [ERROR] Co loi khi bien dich!
    echo Vui long kiem tra lai source code
    echo.
    pause
    exit /b 1
) else (
    echo [SUCCESS] Bien dich thanh cong!
)

echo.
echo [INFO] Dang khoi chay ung dung...
echo.
java -cp . ui.Main

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Khong the khoi chay ung dung!
    echo Vui long kiem tra lai Java va cac file nguon
)

echo.
echo [INFO] Ung dung da dong
pause