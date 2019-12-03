package br.com.hbsis.produto;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;

public class ProdutoDTO {
    private long codigoProduto;
    private String nomeProduto;
    private double precoProduto;
    private long linha;
    private long unidades;
    private double peso;
    private LocalDate validade;

    public ProdutoDTO() {

    }

    public ProdutoDTO(long codigoProduto, String nomeProduto, double precoProduto, long linha, long unidades, double peso, LocalDate validade) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.linha = linha;
        this.unidades = unidades;
        this.peso = peso;
        this.validade = validade;
    }


    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getCodigo_produto(),
                produto.getNome_produto(),
                produto.getPreco_produto(),
                produto.getLinha(),
                produto.getUnidade(),
                produto.getPeso(),
                produto.getValidade()
                );
    }

    public long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(long codigoProduto) {
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

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "codigoProduto=" + codigoProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoProduto=" + precoProduto +
                ", linha=" + linha +
                ", unidades=" + unidades +
                ", peso=" + peso +
                ", validade=" + validade +
                '}';
    }
}
