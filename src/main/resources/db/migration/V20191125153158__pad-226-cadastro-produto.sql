create table seg_categoria
(
                id_categoria        BIGINT  IDENTITY (1, 1),
                codigo_categoria    VARCHAR(10) PRIMARY KEY             NOT NULL,
                id_fornecedor       BIGINT              REFERENCES              seg_fornecedores(id),
                categoria           VARCHAR         (150)           NOT NULL
);

create unique index ix_seg_categoria_02 on seg_categoria (categoria asc);



