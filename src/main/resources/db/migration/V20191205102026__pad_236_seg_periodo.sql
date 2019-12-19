CREATE TABLE seg_periodo
(

    id                  BIGINT   IDENTITY(1,1)    PRIMARY KEY      ,
    id_fornecedor        BIGINT                              NOT NULL REFERENCES seg_fornecedores (id),
    data_inicial        DATE                                NOT NULL,
    data_final          DATE                                NOT NULL,
    retirada            DATE                                NOT NULL

);
