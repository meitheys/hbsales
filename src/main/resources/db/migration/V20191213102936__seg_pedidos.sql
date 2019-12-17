CREATE TABLE seg_pedidos
(
    id BIGINT IDENTITY (1,1),
    id_funcionario BIGINT REFERENCES seg_funcionario (id),
    produtos BIGINT REFERENCES seg_produtos (id),
    quantidade BIGINT,
    status VARCHAR(10)
);

