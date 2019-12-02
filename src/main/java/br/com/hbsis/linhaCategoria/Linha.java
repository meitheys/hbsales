package br.com.hbsis.linhaCategoria;

import javax.persistence.*;

@Entity
@Table(name = "seg_linha")
public class Linha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_linha")
    private Long codigo_linha;
    @Column(name = "categoria_linha", unique = true, nullable = false, length = 200)
    private Long categoria_linha;
    @Column(name = "nome_linha", unique = true, nullable = false, length = 200)
    private String nome_linha;

    public Long getCodigo_linha() {
        return codigo_linha;
    }

    public void setCodigo_linha(Long codigo_linha) {
        this.codigo_linha = codigo_linha;
    }

    public Long getCategoria_linha() {
        return categoria_linha;
    }

    public void setCategoria_linha(Long categoria_linha) {
        this.categoria_linha = categoria_linha;
    }

    public String getNome_linha() {
        return nome_linha;
    }

    public void setNome_linha(String nome_linha) {
        this.nome_linha = nome_linha;
    }

    @Override
    public String toString() {
        return "Linha{" +
                "codigo_linha=" + codigo_linha +
                ", categoria_linha=" + categoria_linha +
                ", nome_linha='" + nome_linha + '\'' +
                '}';
    }


}


