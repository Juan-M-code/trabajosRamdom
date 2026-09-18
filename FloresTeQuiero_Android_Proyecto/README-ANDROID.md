# Flores Te Quiero — Proyecto Android

## Requisitos

Instala:

1. JDK 21
2. Maven 3.9+
3. Android Studio
4. Android SDK
5. Android NDK compatible con la versión de GluonFX instalada
6. Configura `ANDROID_SDK_ROOT` para que Maven/GluonFX encuentre el SDK.

Comprueba:

```bash
java -version
mvn -version
```

En Windows, por ejemplo:

```bat
set ANDROID_SDK_ROOT=C:\Users\TU_USUARIO\AppData\Local\Android\Sdk
```

## Generar el APK

Desde la carpeta del proyecto:

```bash
mvn -Pandroid clean gluonfx:build
mvn -Pandroid gluonfx:package
```

El paquete Android se genera dentro de:

```text
target\gluonfx\
```

Busca el archivo `.apk` generado.

## Instalar en un teléfono conectado por USB

Activa las opciones de desarrollador y depuración USB en el teléfono. Luego:

```bash
adb devices
adb install target\gluonfx\*.apk
```

Si el nombre del APK no acepta `*` en tu terminal, escribe el nombre exacto.

## Ejecutar desde Maven

También puedes usar:

```bash
mvn -Pandroid gluonfx:install
```

si tu instalación de GluonFX/Android tiene disponible esa tarea.

## Qué contiene la aplicación

- 🌹 Rosas
- 🌻 Girasoles
- 🌼 Margaritas
- 🌷 Tulipanes
- Selección múltiple
- Mensajes románticos
- Mensaje personalizado
- Animaciones
- Interfaz JavaFX reutilizable en Android

## Importante

Este proyecto utiliza **JavaFX + GluonFX** para llevar la aplicación JavaFX a Android. No es un proyecto Android Studio tradicional hecho con XML/Jetpack Compose.

El APK no puede generarse aquí sin el Android SDK/NDK y el toolchain nativo de GluonFX. Este paquete contiene el código y la configuración necesarios para realizar esa compilación en una PC.
