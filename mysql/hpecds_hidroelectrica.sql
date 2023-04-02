CREATE DATABASE control_consumo_hidroelectricas;

CREATE USER startcode IDENTIFIED BY 'startcode';
GRANT ALL ON control_consumo_hidroelectricas.* TO startcode;

USE control_consumo_hidroelectricas;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS historico_embalses;
DROP TABLE IF EXISTS previsiones_consumo_agua;
DROP TABLE IF EXISTS previsiones_precipitacion;
DROP TABLE IF EXISTS centrales;
DROP TABLE IF EXISTS embalses;
SET foreign_key_checks = 1;

CREATE TABLE embalses (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    area_en_hm2 FLOAT,
    limite_inferior_en_hm3 FLOAT, 
    limite_superior_en_hm3 FLOAT,
    aportacion_media_anual_en_hm3 FLOAT,
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE centrales (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    ubicacion VARCHAR(50),
    potencia_instalada_en_mw FLOAT,
    latitud FLOAT,
    longitud FLOAT,
    embalse INT,
    CONSTRAINT PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (embalse) REFERENCES embalses (id)
);

CREATE TABLE previsiones_embalses (
    embalse INT NOT NULL,
    fecha_mes DATE NOT NULL,
    volumen_en_hm3 FLOAT,
    consumo_en_hm3 FLOAT,
    precipitacion_en_mm_por_m2 FLOAT,
    CONSTRAINT PRIMARY KEY (embalse, fecha_mes),
    CONSTRAINT FOREIGN KEY (embalse) REFERENCES embalses (id)
);

INSERT INTO embalses (nombre, area_en_hm2, limite_inferior_en_hm3, limite_superior_en_hm3, aportacion_media_anual_en_hm3) VALUES
('Embalse de Aldeadavila',           368, 58.27, 114.3, 12404.15),
('Embalse de Villarino',             8650, 236, 2649, 1760),
('Embalse de Cortes-La Muela',       115, 2, 22, 626.5),
('Embalse de Saucelle',              589, 114, 182, 8642.6),
('Embalse de Cedillo',               1400, 100, 260, 7277),
('Embalse de Estany-Gento Sallente', 31, 1, 6, 30.7),
('Embalse de Tajo de la Encantada',  34, 0, 3, 25.4),
('Embalse de Aguayo',                183, 6, 23, 24.5),
('Embalse de Mequinenza',            7720, 194, 1534, 7121.2),
('Embalse de Mora de Luna',          308, 49.8, 103.2, 506.3);

INSERT INTO centrales (nombre, ubicacion, potencia_instalada_en_mw, latitud, longitud, embalse) VALUES 
('Central de Aldeadavila',          'Salamanca', 1243, 41.21211159893668, -6.685594897938708, 1),
('Central de Villarino',            'Salamanca', 857,  41.26547534089663, -6.495000406071093, 2),
('Central de Cortes-La Muela',      'Valencia',  630,  39.26920917847571, -0.9187704088862684, 3),
('Central de Saucelle',             'Salamanca', 520,  41.03809694749071, -6.802881200882077, 4),
('Cedillo',                         'Caceres',   500,  39.66779396509576, -7.539474589040334, 5),
('Estany-Gento Sallente',           'Lleida',    468,  42.5073217441974, 0.990033255645025, 6),
('Central de Tajo de la Encantada', 'Malaga',    360,  36.90878214306153, -4.76308581446198, 7),
('Central de Aguayo',               'Cantabria', 360,  43.097493232291775, -3.9998186165069036, 8),
('Mequinenza',                      'Zaragoza',  324,  41.36934224588879, 0.2742791636874091, 9),
('Mora de Luna',                    'Leon',      80,   42.82296141753982, -5.838159455507269, 10);

INSERT INTO previsiones_embalses VALUES
(1, '2023-01-01', 92.3, 58.7, 62),
(1, '2023-02-01', 82.3, 63.6, 71),
(1, '2023-03-01', 81.4, 60.4, 64),
(1, '2023-04-01', 79.4, 67.7, 58),

(2, '2023-01-01', 1286, 456.1, 76),
(2, '2023-02-01', 1021, 426.6, 62),
(2, '2023-03-01', 1546, 689.7, 68),
(2, '2023-04-01', 1754, 840.2, 74),

(3, '2023-01-01', 15, 3, 57),
(3, '2023-02-01', 12, 5, 56),
(3, '2023-03-01', 19, 8, 59),
(3, '2023-04-01', 10, 7, 62),

(4, '2023-01-01', 120, 89.6, 60),
(4, '2023-02-01', 146.3, 110, 63),
(4, '2023-03-01', 162.5, 142, 61),
(4, '2023-04-01', 135, 92.6, 38),

(5, '2023-01-01', 100.3, 86.7, 62),
(5, '2023-02-01', 142, 124.4, 71),
(5, '2023-03-01', 244.5, 200, 73),
(5, '2023-04-01', 236, 100.2, 68),

(6, '2023-01-01', 300, 400, 300),
(6, '2023-02-01', 200, 300, 200),
(6, '2023-03-01', 400, 200, 400),
(6, '2023-04-01', 500, 100, 500),

(7, '2023-01-01', 300, 400, 300),
(7, '2023-02-01', 200, 300, 200),
(7, '2023-03-01', 400, 200, 400),
(7, '2023-04-01', 500, 100, 500),

(8, '2023-01-01', 300, 400, 300),
(8, '2023-02-01', 200, 300, 200),
(8, '2023-03-01', 400, 200, 400),
(8, '2023-04-01', 500, 100, 500),

(9, '2023-01-01', 300, 400, 300),
(9, '2023-02-01', 200, 300, 200),
(9, '2023-03-01', 400, 200, 400),
(9, '2023-04-01', 500, 100, 500),

(10, '2023-01-01', 300, 400, 300),
(10, '2023-02-01', 200, 300, 200),
(10, '2023-03-01', 400, 200, 400),
(10, '2023-04-01', 500, 100, 500);