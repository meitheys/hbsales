create table seg_fornecedores
(
                id          BIGINT IDENTITY (1, 1)   PRIMARY KEY NOT NULL,
                razao       VARCHAR(100)                        NOT NULL,
                cnpj        VARCHAR(14)                         NOT NULL,
                nome        VARCHAR(100)                        NOT NULL,
                endereco    VARCHAR(100)                        NOT NULL,
                telefone    VARCHAR(12)                         NOT NULL,
                email       VARCHAR(50)                         NOT NULL,

);

create unique index ix_seg_fornecedores_01 on seg_fornecedores (id asc);
create unique index ix_seg_fornecedores_02 on seg_fornecedores (razao asc);

