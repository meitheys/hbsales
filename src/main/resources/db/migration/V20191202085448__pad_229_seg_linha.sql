create table seg_linha
(
                codigo_linha        BIGINT          IDENTITY (1, 1),
                categoria_linha     BIGINT                           REFERENCES  seg_categoria(codigo_categoria),
                nome_linha          VARCHAR         (150)                                                           NOT NULL
);