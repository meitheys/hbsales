CREATE TABLE seg_produtos
(
    id BIGINT IDENTITY (1,1),
    codigo_produto VARCHAR (10),
    nome_produto VARCHAR (200),
    preco_produto DECIMAL ,
    linha BIGINT REFERENCES seg_linha (id),
    unidades BIGINT,
    peso DECIMAL ,
    unidade_peso VARCHAR (10),
    validade DATE

);

