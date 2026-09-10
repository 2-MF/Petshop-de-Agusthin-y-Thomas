-- =====================================================
-- SCRIPT SQL - PETSHOP DATABASE
-- Modelo Entidad-Relación
-- =====================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS petshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE petshop;

-- =====================================================
-- TABLA: FUNCIONARIOS
-- =====================================================
CREATE TABLE funcionarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'FUNCIONARIO', 'BAÑADOR') NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: CLIENTES
-- =====================================================
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(255),
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: MASCOTAS
-- Una mascota pertenece a un único cliente (dueño)
-- Un cliente puede tener varias mascotas
-- =====================================================
CREATE TABLE mascotas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(50),
    fecha_nacimiento DATE,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

-- =====================================================
-- TABLA: AGENDA (Horarios para bañar perros)
-- Máximo 10 baños por día (validado en aplicación)
-- El bañador lee la agenda para saber sus horarios
-- =====================================================
CREATE TABLE agendas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    servicio ENUM('CORTE_PELO', 'BAÑO', 'CORTE_Y_BAÑO') NOT NULL,
    estado ENUM('PENDIENTE', 'COMPLETADO', 'CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    mascota_id BIGINT NOT NULL,
    banador_id BIGINT NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,
    FOREIGN KEY (banador_id) REFERENCES funcionarios(id) ON DELETE CASCADE,
    UNIQUE KEY uk_agenda_banador_horario (fecha, hora, banador_id),
    UNIQUE KEY uk_agenda_mascota_horario (fecha, hora, mascota_id)
);

-- =====================================================
-- TABLA: SOLICITUDES
-- Solicitud de servicio con 3 opciones:
-- CORTE_PELO, BAÑO, CORTE_Y_BAÑO
-- =====================================================
CREATE TABLE solicitudes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    mascota_id BIGINT NOT NULL,
    tipo_servicio ENUM('CORTE_PELO', 'BAÑO', 'CORTE_Y_BAÑO') NOT NULL,
    estado ENUM('PENDIENTE', 'APROBADA', 'RECHAZADA', 'COMPLETADA') NOT NULL DEFAULT 'PENDIENTE',
    fecha_solicitud DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_preferencia DATETIME,
    observaciones TEXT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE
);

-- =====================================================
-- ÍNDICES ADICIONALES
-- =====================================================
CREATE INDEX idx_agenda_fecha ON agendas(fecha);
CREATE INDEX idx_agenda_banador ON agendas(banador_id);
CREATE INDEX idx_agenda_mascota ON agendas(mascota_id);
CREATE INDEX idx_solicitud_cliente ON solicitudes(cliente_id);
CREATE INDEX idx_solicitud_mascota ON solicitudes(mascota_id);
CREATE INDEX idx_mascota_cliente ON mascotas(cliente_id);

-- =====================================================
-- DATOS INICIALES (Opcional)
-- =====================================================
-- Insertar un administrador por defecto
INSERT INTO funcionarios (nombre, email, password, rol, telefono) VALUES
('Admin Principal', 'admin@petshop.com', 'admin123', 'ADMINISTRADOR', '555-0001');

-- Insertar un bañador por defecto
INSERT INTO funcionarios (nombre, email, password, rol, telefono) VALUES
('Juan Bañador', 'juan@petshop.com', 'juan123', 'BAÑADOR', '555-0002');

-- Insertar un funcionario común por defecto
INSERT INTO funcionarios (nombre, email, password, rol, telefono) VALUES
('María Recepcionista', 'maria@petshop.com', 'maria123', 'FUNCIONARIO', '555-0003');

-- Insertar un cliente de ejemplo
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES
('Carlos Pérez', 'carlos@email.com', '555-1001', 'Av. Principal 123');

-- Insertar una mascota de ejemplo
INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, cliente_id) VALUES
('Rex', 'Perro', 'Labrador', '2020-05-15', 1);
