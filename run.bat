@echo off
:: Stay right inside the current folder where this batch file lives
cd /d "%~dp0"

echo ========================================
echo    Compiling ChronoSphere Files...    
echo ========================================

:: Compile all java files directly in this directory
javac *.java

:: If compilation works, launch Main directly
if %errorlevel% equ 0 (
    echo Compilation successful! Launching Cyber Hub GUI...
    start java Main
) else (
    echo Compilation failed. Check for syntax errors.
    pause
)