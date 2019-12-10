package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seg_categoria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private long idCategoria;
    @Column(name = "codigo_categoria",  length = 10)
    private String codigoCategoria;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;
    @Column(name = "categoria", unique = false, nullable = false, length = 100)
    private String nomeCategoria;

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", codigoCategoria='" + codigoCategoria + '\'' +
                ", fornecedor=" + fornecedor +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}