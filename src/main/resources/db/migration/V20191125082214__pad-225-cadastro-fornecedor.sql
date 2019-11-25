create table seg_fornecedores
(
    id    BIGINT IDENTITY (1, 1) NOT NULL,
    razao VARCHAR(100)           NOT NULL,
    cnpj VARCHAR(255)           NOT NULL,
    nome  VARCHAR(100)            NOT NULL,
    endereco VARCHAR(100)            NOT NULL,
    telefone VARCHAR(16)            NOT NULL,
    email VARCHAR(100)            NOT NULL,





);

create unique index ix_seg_fornecedores_01 on seg_fornecedores (id asc);
create unique index ix_seg_fornecedores_02 on seg_fornecedores (razao asc);
create unique index ix_seg_fornecedores_03 on seg_fornecedores (cnpj asc);
create unique index ix_seg_fornecedores_04 on seg_fornecedores (telefone asc);