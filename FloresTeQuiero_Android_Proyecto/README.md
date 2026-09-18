# Flores Te Quiero — JavaFX + Gluon Mobile

Aplicación multiplataforma para PC y móvil. Muestra flores animadas y mensajes románticos.
El usuario puede seleccionar qué flores aparecen y cambiar el mensaje.

## Tecnologías
- Java 21
- JavaFX
- GluonFX para empaquetado móvil
- Maven

## Funciones
- Rosas
- Girasoles
- Margaritas
- Tulipanes
- Selección múltiple de flores
- Mensajes: TE QUIERO, TE AMO, ME ENCANTAS, ERES ESPECIAL
- Mensaje personalizado
- Animación suave de flores
- Diseño adaptable

## PC
Necesitas JDK 21 y Maven.

```bash
mvn clean javafx:run
```

## Android
Para generar un APK necesitas instalar Android Studio/Android SDK y configurar GluonFX.
Después:

```bash
mvn -Pandroid gluonfx:build
mvn -Pandroid gluonfx:package
```

El APK generado quedará dentro de `target/gluonfx/`.

> JavaFX no tiene un empaquetado Android oficial directo. Esta plantilla usa GluonFX para reutilizar la misma interfaz y lógica JavaFX en Android.

## iOS
Para iOS se necesita macOS + Xcode.

```bash
mvn -Pios gluonfx:build
mvn -Pios gluonfx:package
```

## Personalización
Las flores se dibujan con JavaFX, por lo que no necesitas imágenes externas. Puedes reemplazar cada dibujo por PNG/SVG posteriormente.
