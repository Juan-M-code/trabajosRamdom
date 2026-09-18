@echo off
setlocal
echo ============================================
echo Flores Te Quiero - Generador de APK Android
echo ============================================
echo.
if "%JAVA_HOME%"=="" (
  echo ERROR: JAVA_HOME no esta configurado.
  echo Instala JDK 21 y configura JAVA_HOME.
  pause
  exit /b 1
)
if "%ANDROID_SDK_ROOT%"=="" (
  echo ADVERTENCIA: ANDROID_SDK_ROOT no esta configurado.
  echo Configuralo apuntando al Android SDK.
  echo.
)
call mvn -Pandroid clean gluonfx:build
if errorlevel 1 (
  echo.
  echo ERROR durante la compilacion.
  pause
  exit /b 1
)
call mvn -Pandroid gluonfx:package
if errorlevel 1 (
  echo.
  echo ERROR al empaquetar el APK.
  pause
  exit /b 1
)
echo.
echo APK generado en target\gluonfx\
pause
