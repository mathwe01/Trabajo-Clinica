-- =========================================================
-- Proyecto: Historias Clínicas Digitales - Centro de Salud Ganímedes
-- Base de datos según el diagrama de clases (Paciente, HistoriaClinica, Atencion)
-- Autor: Mathew (Base de datos + Conexión Java-MySQL)
-- =========================================================

CREATE DATABASE IF NOT EXISTS bd_centrosaludganimedes
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE bd_centrosaludganimedes;

-- ---------------------------------------------------------
-- Tabla: pacientes
-- Corresponde a la clase "Paciente" del diagrama
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS pacientes (
    id_paciente        INT AUTO_INCREMENT PRIMARY KEY,
    nombre             VARCHAR(60)  NOT NULL,
    apellido           VARCHAR(60)  NOT NULL,
    documento          VARCHAR(20)  NOT NULL UNIQUE,   -- DNI del paciente
    fecha_nacimiento   DATE         NOT NULL,
    usuario            VARCHAR(40)  NOT NULL UNIQUE,   -- para InicioDeSesionDePaciente()
    contrasena_hash    VARCHAR(255) NOT NULL,          -- NUNCA texto plano (Ley 29733)
    fecha_registro     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------
-- Tabla: historias_clinicas
-- Corresponde a la clase "HistoriaClinica" del diagrama
-- Relación: 1 paciente -> 1 historia clínica
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS historias_clinicas (
    id_historia    INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente    INT NOT NULL UNIQUE,
    fecha          DATE NOT NULL,
    antecedentes   TEXT,
    diagnostico    TEXT,
    tratamiento    TEXT,
    CONSTRAINT fk_historia_paciente
        FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ---------------------------------------------------------
-- Tabla: atenciones
-- Corresponde a la clase "Atencion" del diagrama
-- Relación: 1 historia clínica -> muchas atenciones
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS atenciones (
    id_atencion    INT AUTO_INCREMENT PRIMARY KEY,
    id_historia    INT NOT NULL,
    fecha          DATE NOT NULL,
    motivo         VARCHAR(255) NOT NULL,
    CONSTRAINT fk_atencion_historia
        FOREIGN KEY (id_historia) REFERENCES historias_clinicas(id_historia)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ---------------------------------------------------------
-- Datos de prueba (opcional, para verificar que todo funciona)
-- ---------------------------------------------------------
INSERT INTO pacientes (nombre, apellido, documento, fecha_nacimiento, usuario, contrasena_hash)
VALUES ('Juan', 'Perez Rios', '71234567', '1998-05-12', 'jperez', 'CAMBIAR_POR_HASH');

INSERT INTO historias_clinicas (id_paciente, fecha, antecedentes, diagnostico, tratamiento)
VALUES (1, CURDATE(), 'Sin antecedentes relevantes', 'Por definir', 'Por definir');

INSERT INTO atenciones (id_historia, fecha, motivo)
VALUES (1, CURDATE(), 'Consulta general');
