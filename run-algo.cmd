@echo off
setlocal
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.3.9-hotspot"
set "ROOT=%~dp0"
cd /d "%ROOT%"
if "%~1"=="" (
  echo Usage: run-algo.cmd ClassName
  exit /b 1
)
if not exist bin mkdir bin
"%JAVA_HOME%\bin\javac.exe" -g -encoding UTF-8 -d bin "algorithms\solutions\%~1.java"
if errorlevel 1 exit /b 1
"%JAVA_HOME%\bin\java.exe" -cp bin %~1
