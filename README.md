# Sistema de Inscripción de Estudiantes

Aplicación de escritorio en JavaFX con JPA y MySQL para gestionar estudiantes, cursos e inscripciones.

## Requisitos
- JDK 17 o superior
- Docker y docker-compose

## Preparar base de datos

```bash
docker-compose up -d
```
Esto levantará un contenedor de MySQL con la base `escuela` inicializada desde `database.sql`.

## Configuración JPA
La conexión se encuentra en `src/META-INF/persistence.xml`. Ajusta usuario, contraseña o URL si es necesario.

## Ejecutar la aplicación

Compila y ejecuta el proyecto con tu IDE favorito o desde línea de comandos:

```bash
javac -cp lib/*:. $(find src -name "*.java")
java -cp lib/*:src App
```

La interfaz permite:
- CRUD de estudiantes y cursos
- Inscribir y remover estudiantes de cursos
- Búsquedas básicas

El `App` carga un menú principal con botones para navegar entre las vistas de estudiantes, cursos e inscripciones o cerrar la aplicación. Cada vista se encuentra en `src/vistas` para mantener el código modular y fácil de mantener.

Los estilos están definidos en `src/styles/main.css`.
