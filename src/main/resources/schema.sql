-- =============================================
-- Script de creación de la base de datos
-- Gestión de Usuarios - Arquitectura Hexagonal
-- =============================================

CREATE DATABASE IF NOT EXISTS Enfermedades_db;

USE Enfermedades_db;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$sfwkajsls.fktQb0Ngw5cOiSiV3z4LIwbucSAy4sw2wyNlxbUBt4q',
    'ADMIN',
    'ACTIVE'
);

CREATE TABLE Enfermedades (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    nombreCientifico VARCHAR(100) NOT NULL,
    nivelGravedad VARCHAR(20) NOT NULL CHECK (nivelGravedad IN('Leve', 'Moderada', 'Grave', 'Critica')),
    sintomas VARCHAR(500) NOT NULL,
    medicamentos VARCHAR(200) NOT NULL,
    esContagiosa BOOLEAN NOT NULL DEFAULT FALSE,
    esCubiertaPorPos BOOLEAN NOT NULL DEFAULT FALSE,
    requiereIncapacidad BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO Enfermedades
(nombre, nombreCientifico, nivelGravedad, sintomas, medicamentos, esContagiosa, esCubiertaPorPos, requiereIncapacidad)
VALUES
    ('Gripe común', 'Influenza', 'Leve', 'Fiebre, tos, congestión nasal, dolor de garganta', 'Paracetamol, Loratadina', TRUE, TRUE, FALSE),
    ('COVID-19', 'SARS-CoV-2', 'Grave', 'Fiebre, tos seca, dificultad respiratoria, anosmia', 'Paracetamol, Oxígeno suplementario', TRUE, TRUE, TRUE),
    ('Varicela', 'Varicella zoster', 'Moderada', 'Erupción cutánea, picazón, fiebre', 'Aciclovir, Antihistamínicos', TRUE, TRUE, TRUE),
    ('Hipertensión arterial', 'Hipertensión esencial', 'Moderada', 'Dolor de cabeza, mareo, zumbido en los oídos', 'Losartán, Enalapril', FALSE, TRUE, FALSE),
    ('Diabetes tipo 2', 'Diabetes mellitus tipo 2', 'Grave', 'Sed excesiva, fatiga, visión borrosa', 'Metformina, Insulina', FALSE, TRUE, TRUE),
    ('Resfriado común', 'Rhinovirus', 'Leve', 'Estornudos, congestión nasal, malestar leve', 'Descongestionante, Vitamina C', TRUE, FALSE, FALSE);
