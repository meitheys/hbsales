ALTER TABLE seg_pedidos ADD PRIMARY KEY (id);

CREATE TABLE seg_item (

    id          BIGINT IDENTITY(1,1),
    id_produto  BIGINT REFERENCES seg_produtos  (id),
    id_pedido   BIGINT REFERENCES seg_pedidos   (id),
    quantidade  BIGINT                                  NOT NULL,


    CONSTRAINT "FK__seg_item__id_ped__4BCC3ABA" FOREIGN KEY ("id_pedido") REFERENCES seg_pedidos (id),
    CONSTRAINT "FK__seg_item__id_prod__4BCC3ABA" FOREIGN KEY ("id_produto") REFERENCES seg_pedidos (id),

);


