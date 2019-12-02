CREATE TABLE seg_produtos

                (codigo_produto BIGINT IDENTITY (1, 1) NOT NULL             ,

                nome_produto VARCHAR                                        ,

                preco_produto DECIMAL                                       ,

                codigo_linha BIGINT REFERENCES seg_linha(codigo_linha)      ,

                unidades BIGINT                                             ,

                peso DECIMAL                                                ,

                validade DATE                                               );