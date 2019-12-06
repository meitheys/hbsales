CREATE TABLE seg_funcionario
(
    id BIGINT IDENTITY(1,1),
    nome_funcionario VARCHAR (100),
    email VARCHAR (100)
);

create unique index ix_seg_funcionario_01 on seg_funcionario (email asc);
