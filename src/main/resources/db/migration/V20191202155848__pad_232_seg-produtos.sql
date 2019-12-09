CREATE TABLE seg_produtos
(

    codigo_produto BIGINT IDENTITY (1,1),
    nome_produto VARCHAR (100),
    preco_produto DECIMAL ,
    linha VARCHAR(10) REFERENCES seg_linha (codigo_linha),
    unidades BIGINT,
    peso DECIMAL ,
    validade DATE

);

