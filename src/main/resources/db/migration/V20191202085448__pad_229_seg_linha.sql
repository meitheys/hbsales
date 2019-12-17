create table seg_linha
(
                id          BIGINT IDENTITY(1,1) PRIMARY KEY,
                codigo_linha        VARCHAR(10)                         ,
                categoria_linha     VARCHAR(10)                  REFERENCES  seg_categoria(codigo_categoria) ,
                nome_linha          VARCHAR     (50)                                                           NOT NULL
);

create unique index ix_seg_linha_01 on seg_linha (codigo_linha asc);