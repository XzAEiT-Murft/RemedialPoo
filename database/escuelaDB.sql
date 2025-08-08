IF DB_ID('escuela') IS NULL
    CREATE DATABASE escuela;
GO

USE escuela;
GO

IF OBJECT_ID('estudiantes', 'U') IS NULL
CREATE TABLE estudiantes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE
);
GO

IF OBJECT_ID('cursos', 'U') IS NULL
CREATE TABLE cursos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    creditos INT NOT NULL
);
GO

IF OBJECT_ID('inscripciones', 'U') IS NULL
CREATE TABLE inscripciones (
    estudiante_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (estudiante_id, curso_id),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);
GO