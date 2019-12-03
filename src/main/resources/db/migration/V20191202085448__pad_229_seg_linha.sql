create table seg_linha
(
                codigo_linha        BIGINT          IDENTITY (1, 1)        PRIMARY KEY            ,
                categoria_linha     BIGINT                    REFERENCES  seg_categoria(codigo_categoria),
                nome_linha          VARCHAR     (150)                                                           NOT NULL
);

create unique index ix_seg_linha_01 on seg_linha (codigo_linha asc);