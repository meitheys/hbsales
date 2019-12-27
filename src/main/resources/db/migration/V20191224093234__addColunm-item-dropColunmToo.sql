ALTER TABLE seg_item DROP COLUMN item_name;
ALTER TABLE seg_item ADD id_pedido BIGINT REFERENCES seg_pedidos (id);