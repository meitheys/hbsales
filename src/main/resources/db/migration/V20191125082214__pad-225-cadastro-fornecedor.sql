create table seg_fornecedores
(
                id          BIGINT IDENTITY (1, 1)   PRIMARY KEY NOT NULL,
                razao       VARCHAR(100)                        ,
                cnpj        VARCHAR(255)                        NOT NULL,
                nome        VARCHAR(100)                        ,
                endereco    VARCHAR(100)                        ,
                telefone    VARCHAR(16)                         ,
                email       VARCHAR(100)                        ,





);

create unique index ix_seg_fornecedores_01 on seg_fornecedores (id asc);
create unique index ix_seg_fornecedores_02 on seg_fornecedores (razao asc);
create unique index ix_seg_fornecedores_03 on seg_fornecedores (cnpj asc);
