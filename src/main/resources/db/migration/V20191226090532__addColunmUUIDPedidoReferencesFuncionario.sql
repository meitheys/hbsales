ALTER TABLE seg_pedidos ADD uuid VARCHAR(36);
ALTER TABLE seg_pedidos ADD fornecedor BIGINT;
ALTER TABLE seg_pedidos ADD CONSTRAINT fk_fornecedor FOREIGN KEY (fornecedor) REFERENCES seg_fornecedores (id);