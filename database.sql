CREATE DATABASE IF NOT EXISTS escuela;
USE escuela;

CREATE TABLE IF NOT EXISTS estudiantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    creditos INT NOT NULL
);

CREATE TABLE IF NOT EXISTS inscripciones (
    estudiante_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (estudiante_id, curso_id),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);
