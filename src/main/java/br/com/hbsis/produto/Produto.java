package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.Linha;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private long idProduto;
    @Column(name = "codigo_produto", unique = true, nullable = false)
    private String codigoProduto;
    @Column(name = "nome_produto", unique = false, nullable = false)
    private String nomeProduto;
    @Column(name = "preco_produto",  unique = false, nullable = false)
    private double precoProduto;
    @ManyToOne
    @JoinColumn(name = "linha", nullable = false, referencedColumnName = "id")
    private Linha linha;
    @Column(name = "unidades", unique = false, nullable = false)
    private long unidade;
    @Column(name = "peso", unique = false, nullable = false)
    private double peso;
    @Column(name = "unidade_peso")
    private String unidadePeso;
    @Column(name = "validade", unique = false, nullable = false)
    private LocalDate validade;

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public String getUnidadePeso() {
        return unidadePeso;
    }

    public void setUnidadePeso(String unidadePeso) {
        this.unidadePeso = unidadePeso;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    public long getUnidade() {
        return unidade;
    }

    public void setUnidade(long unidade) {
        this.unidade = unidade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", codigoProduto=" + codigoProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoProduto=" + precoProduto +
                ", linha=" + linha +
                ", unidade=" + unidade +
                ", peso=" + peso +
                ", unidadePeso=" + unidadePeso +
                ", validade=" + validade +
                '}';
    }
}
