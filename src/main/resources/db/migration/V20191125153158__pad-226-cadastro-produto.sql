create table seg_categoria
(
                codigo_categoria     BIGINT IDENTITY (1, 1)      NOT NULL,
                id_fornecedor      BIGINT             REFERENCES  seg_fornecedores(id),
                categoria       VARCHAR         (150)       NOT NULL
);

create unique index ix_seg_categoria_01 on seg_categoria (id_fornecedor asc);
create unique index ix_seg_categoria_02 on seg_categoria (categoria asc);



