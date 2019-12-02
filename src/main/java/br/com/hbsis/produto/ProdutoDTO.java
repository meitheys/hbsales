package br.com.hbsis.produto;

import java.util.Date;

public class ProdutoDTO {
    private long codigo_produto;
    private String nome_produto;
    private double preco_produto;
    private long codigo_linha;
    private long unidades;
    private double peso;
    private Date validade;

    public ProdutoDTO() {

    }

    public ProdutoDTO(long codigo_produto, String nome_produto, double preco_produto, long codigo_linha, long unidades, double peso, Date validade) {
        this.codigo_produto = codigo_produto;
        this.nome_produto = nome_produto;
        this.preco_produto = preco_produto;
        this.codigo_linha = codigo_linha;
        this.unidades = unidades;
        this.peso = peso;
        this.validade = validade;
    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getCodigo_produto(),
                produto.getNome_produto(),
                produto.getPreco_produto(),
                produto.getCodigo_linha(),
                produto.getUnidade(),
                produto.getPeso(),
                produto.getValidade()
                );
    }

    public long getCodigo_produto() {
        return codigo_produto;
    }

    public void setCodigo_produto(long codigo_produto) {
        this.codigo_produto = codigo_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public double getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(double preco_produto) {
        this.preco_produto = preco_produto;
    }

    public long getCodigo_linha() {
        return codigo_linha;
    }

    public void setCodigo_linha(long codigo_linha) {
        this.codigo_linha = codigo_linha;
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

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "codigo_produto=" + codigo_produto +
                ", nome_produto='" + nome_produto + '\'' +
                ", preco_produto=" + preco_produto +
                ", codigo_linha=" + codigo_linha +
                ", unidades=" + unidades +
                ", peso=" + peso +
                ", validade='" + validade + '\'' +
                '}';
    }
}
