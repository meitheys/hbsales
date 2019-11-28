package br.com.hbsis.categoria;



import br.com.hbsis.fornecedor.Fornecedor;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;

@Entity
@Table(name = "seg_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    @CsvBindByName(column = "id_fornecedor")
    private Fornecedor fornecedor;
    @Column(name = "categoria", unique = false, nullable = false, length = 100)
    @CsvBindByName(column = "categoria")
    private String nomeCategoria;


    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", id_fornecedor" + fornecedor + '\'' +
                ", categoria='" + nomeCategoria + '\'' +
                '}';
    }
}