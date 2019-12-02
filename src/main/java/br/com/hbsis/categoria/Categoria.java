package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;

@Entity
@Table(name = "seg_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @CsvBindByName(column = "id")
    @Column(name = "codigo_categoria",  length = 100)
    private String codigoCategoria;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
//    @CsvBindByName(column = "id_fornecedor")
    private Fornecedor fornecedor;
    @Column(name = "categoria", unique = false, nullable = false, length = 100)
//    @CsvBindByName(column = "categoria")
    private String nomeCategoria;

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
                "codigoCategoria='" + codigoCategoria + '\'' +
                ", fornecedor=" + fornecedor +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}