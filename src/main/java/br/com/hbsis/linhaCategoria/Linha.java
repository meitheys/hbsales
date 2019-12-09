package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "seg_linha")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "codigo_linha")
    private String codigoLinha;
    @ManyToOne
    @JoinColumn(name = "categoria_linha", unique = true, nullable = false, referencedColumnName = "codigo_categoria")
    private Categoria categoriaLinha;
    @Column(name = "nome_linha", unique = true, nullable = false, length = 200)
    private String nomeLinha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public Categoria getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(Categoria categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }


    @Override
    public String toString() {
        return "Linha{" +
                "id=" + id +
                ", codigo_linha='" + codigoLinha + '\'' +
                ", categoria_linha=" + categoriaLinha +
                ", nome_linha='" + nomeLinha + '\'' +
                '}';
    }

}


