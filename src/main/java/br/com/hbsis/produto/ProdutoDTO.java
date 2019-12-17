package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.Linha;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProdutoDTO {
    private long idProduto;
    private String codigoProduto;
    private String nomeProduto;
    private double precoProduto;
    private long linha;
    private long unidades;
    private double peso;
    private String unidadePeso;
    private LocalDate validade;

    public ProdutoDTO() {

    }

    public ProdutoDTO(long idProduto, String codigoProduto, String nomeProduto, double precoProduto, long linha, long unidades, String unidadePeso, double peso, LocalDate validade) {
        this.idProduto = idProduto;
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.linha = linha;
        this.unidades = unidades;
        this.peso = peso;
        this.unidadePeso = unidadePeso;
        this.validade = validade;
    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getIdProduto(),
                produto.getCodigoProduto(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getLinha().getId(),
                produto.getUnidade(),
                produto.getUnidadePeso(),
                produto.getPeso(),
                produto.getValidade()
        );
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
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

    public long getLinha() {
        return linha;
    }

    public void setLinha(long linha) {
        this.linha = linha;
    }

    public long getUnidades() {
        return unidades;
    }

    public void setUnidades(long unidades) {
        this.unidades = unidades;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getUnidadePeso() {
        return unidadePeso;
    }

    public void setUnidadePeso(String unidadePeso) {
        this.unidadePeso = unidadePeso;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "idProduto=" + idProduto +
                ", codigoProduto='" + codigoProduto + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoProduto=" + precoProduto +
                ", linha=" + linha +
                ", unidades=" + unidades +
                ", peso=" + peso +
                ", unidadePeso='" + unidadePeso + '\'' +
                ", validade=" + validade +
                '}';
    }
}
