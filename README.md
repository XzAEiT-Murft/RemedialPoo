# 📚 Sistema de Gestión Escolar

¡Bienvenido! Este proyecto es una aplicación de escritorio hecha con **JavaFX** y **JPA** para administrar estudiantes, cursos y sus inscripciones. Permite crear, consultar, actualizar y eliminar registros de forma "intuitiva", una disculpa si el proyecto no cumple con las condiciones de ser intuitiva la interface, pero con la limitante del tiempo no pude hacer mucho para mejorarla.

---

## ✨ Funcionalidades
- **Gestión de estudiantes**: crear, listar, actualizar y eliminar alumnos.
- **Gestión de cursos**: crear, actualizar y eliminar cursos.
- **Inscripciones**: asignar y quitar cursos a cada estudiante.
- **Validaciones**: no es posible eliminar un curso que tenga estudiantes inscritos.

---

## 🛠️ Especificaciones Técnicas
- **Lenguaje**: Java 22 (JDK 22)
- **Interfaz gráfica**: JavaFX
- **Persistencia**: Jakarta Persistence (JPA) 3.0 con EclipseLink
- **Base de datos**: Microsoft SQL Server
- **Patrón**: MVC (Model–View–Controller)

---

## 📦 Configuración del Proyecto
1. **Clona el repositorio**
   ```bash
   git clone https://github.com/XzAEiT-Murft/RemedialPoo.git
   cd RemedialPoo
   ```
2. **Configura la base de datos**
   - Crea una base de datos llamada `Escuela` en SQL Server.
   - Actualiza las credenciales en `src/META-INF/persistence.xml` si es necesario.
3. **Compila el proyecto**
   ```bash
   javac $(find src -name '*.java') -d build
   ```
   > ℹ️ Asegúrate de tener las librerías de JavaFX y Jakarta Persistence en el classpath.
4. **Ejecuta la aplicación**
   ```bash
   java -cp build App
   ```

---

## 🧱 Estructura del Proyecto
```
RemedialPoo/
├── database/               # Scripts SQL y archivos de base de datos
├── src/
│   ├── controllers/        # Controladores de la lógica de negocio
│   ├── models/             # Entidades JPA para Estudiante y Curso
│   ├── utils/              # Utilidades como JPAUtil
│   └── views/              # Interfaces JavaFX
└── README.md               # Este archivo
```

---

## 🧩 Requisitos
- JDK 22 instalado y configurado
- SQL Server en ejecución
- Librerías de JavaFX y Jakarta Persistence disponibles
- Editor recomendado: VS Code o IntelliJ IDEA

---

## 🤝 Dudas
Si encuentra algún problema o tiene dudas de como funciona profa me puede escribir un comentario privado al classroom

---

Hecho con 💻 por **Luis Garcia Cruz**