ALTER TABLE seg_pedidos ADD PRIMARY KEY (id);

CREATE TABLE seg_item (

    id          BIGINT IDENTITY(1,1),
    id_produto  BIGINT REFERENCES seg_produtos  (id),
    id_pedido   BIGINT REFERENCES seg_pedidos   (id),
    quantidade  BIGINT                                  NOT NULL
);