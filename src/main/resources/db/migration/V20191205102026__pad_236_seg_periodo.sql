CREATE TABLE seg_periodo
(

    id                  BIGINT          IDENTITY(1,1),
    id_fornecedor        BIGINT                              NOT NULL,
    data_inicial        DATE                                NOT NULL,
    data_final          DATE                                NOT NULL,
    retirada            DATE                                NOT NULL

);
