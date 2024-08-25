DROP TABLE IF EXISTS publicacion;

DROP TABLE IF EXISTS usuario;

CREATE TABLE
    usuario (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(45) NOT NULL
    );

CREATE TABLE
    publicacion (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        usuario_id BIGINT,
        contenido VARCHAR(280),
        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
    );