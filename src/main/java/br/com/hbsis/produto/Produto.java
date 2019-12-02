package br.com.hbsis.produto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Produto {
    @Id
    @GeneratedValue
    @Column(name = "codigo_produto", unique = true, nullable = false)
    private long codigo_produto;
    @Column(name = "nome_produto", unique = false, nullable = false, length = 100)
    private String nome_produto;
    @Column(name = "preco_produto", unique = false, nullable = false)
    private double preco_produto;
    @Column(name = "codigo_linha", unique = false, nullable = false, length = 100)
    private long codigo_linha;
    @Column(name = "unidades", unique = false, nullable = false, length = 100)
    private long unidade;
    @Column(name = "peso", unique = false, nullable = false, length = 100)
    private double peso;
    @Column(name = "validade", unique = false, nullable = false, length = 100)
    private Date validade;

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

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigo_produto=" + codigo_produto +
                ", nome_produto='" + nome_produto + '\'' +
                ", preco_produto=" + preco_produto +
                ", codigo_linha=" + codigo_linha +
                ", unidade=" + unidade +
                ", peso=" + peso +
                ", validade='" + validade + '\'' +
                '}';
    }
}
