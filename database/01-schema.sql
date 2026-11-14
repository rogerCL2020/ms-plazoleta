
CREATE DATABASE IF NOT EXISTS pragma_plazoleta;
USE pragma_plazoleta;

-- ==========================================
-- 1. RESTAURANTES
-- ==========================================

CREATE TABLE restaurantes (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              nombre VARCHAR(150) NOT NULL,
                              direccion VARCHAR(255) NOT NULL,
                              id_propietario BIGINT NOT NULL,
                              telefono VARCHAR(13) NOT NULL,
                              url_logo VARCHAR(500) NOT NULL,
                              nit VARCHAR(20) NOT NULL,

                              PRIMARY KEY (id),
                              CONSTRAINT uk_restaurantes_nit UNIQUE (nit),

                              CONSTRAINT chk_restaurante_nombre
                                  CHECK (
                                      CHAR_LENGTH(TRIM(nombre)) > 0
                                          AND TRIM(nombre) NOT REGEXP '^[0-9]+$'
                                      ),

                              CONSTRAINT chk_restaurante_nit
                                  CHECK (nit REGEXP '^[0-9]+$'),

                              CONSTRAINT chk_restaurante_telefono
                                  CHECK (telefono REGEXP '^\\+?[0-9]{1,12}$')
);

-- ==========================================
-- 2. CATEGORIAS
-- ==========================================

CREATE TABLE categoria (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion VARCHAR(255) NOT NULL,

                           PRIMARY KEY (id)
);

-- ==========================================
-- 3. PLATOS
-- ==========================================

CREATE TABLE platos (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        nombre VARCHAR(150) NOT NULL,
                        id_categoria BIGINT NOT NULL,
                        descripcion VARCHAR(500) NOT NULL,
                        precio DECIMAL(12,2) NOT NULL,
                        id_restaurante BIGINT NOT NULL,
                        url_imagen VARCHAR(500) NOT NULL,
                        activo BOOLEAN NOT NULL DEFAULT TRUE,

                        PRIMARY KEY (id),

                        CONSTRAINT fk_plato_categoria
                            FOREIGN KEY (id_categoria)
                                REFERENCES categoria(id),

                        CONSTRAINT fk_plato_restaurante
                            FOREIGN KEY (id_restaurante)
                                REFERENCES restaurantes(id),

                        CONSTRAINT chk_plato_precio
                            CHECK (precio > 0)
);

-- ==========================================
-- 4. PEDIDOS
-- ==========================================

CREATE TABLE pedidos (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         id_cliente BIGINT NOT NULL,
                         fecha DATETIME NOT NULL,
                         estado VARCHAR(30) NOT NULL,
                         id_chef BIGINT NULL,
                         id_restaurante BIGINT NOT NULL,

                         PRIMARY KEY (id),

                         CONSTRAINT fk_pedido_restaurante
                             FOREIGN KEY (id_restaurante)
                                 REFERENCES restaurantes(id)
);

-- ==========================================
-- 5. PEDIDOS_PLATOS
-- ==========================================

CREATE TABLE pedidos_platos (
                                id_pedido BIGINT NOT NULL,
                                id_plato BIGINT NOT NULL,
                                cantidad INT NOT NULL,

                                PRIMARY KEY (id_pedido, id_plato),

                                CONSTRAINT fk_pedidos_platos_pedido
                                    FOREIGN KEY (id_pedido)
                                        REFERENCES pedidos(id),

                                CONSTRAINT fk_pedidos_platos_plato
                                    FOREIGN KEY (id_plato)
                                        REFERENCES platos(id),

                                CONSTRAINT chk_pedidos_platos_cantidad
                                    CHECK (cantidad > 0)
);


-- ==========================================
-- 7. RESTAURANTES_EMPLEADOS
-- ==========================================

CREATE TABLE restaurante_empleados (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       id_restaurante BIGINT NOT NULL,
                                       id_empleado BIGINT NOT NULL,

                                       PRIMARY KEY (id),

                                       CONSTRAINT uk_restaurante_empleado
                                           UNIQUE (id_empleado),

                                       CONSTRAINT fk_restaurante_empleado_restaurante
                                           FOREIGN KEY (id_restaurante)
                                               REFERENCES restaurantes(id)
);

-- ==========================================
-- 8. INDICES
-- ==========================================

CREATE INDEX idx_restaurante_propietario
    ON restaurantes(id_propietario);

CREATE INDEX idx_platos_restaurante
    ON platos(id_restaurante);

CREATE INDEX idx_platos_categoria
    ON platos(id_categoria);

CREATE INDEX idx_pedidos_cliente
    ON pedidos(id_cliente);

CREATE INDEX idx_pedidos_chef
    ON pedidos(id_chef);

CREATE INDEX idx_pedidos_restaurante
    ON pedidos(id_restaurante);

CREATE INDEX idx_pedidos_fecha
    ON pedidos(fecha);
