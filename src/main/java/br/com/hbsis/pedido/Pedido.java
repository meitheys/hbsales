package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioDTO;
import br.com.hbsis.periodo.Periodo;
import br.com.hbsis.produto.Produto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "produtos", referencedColumnName = "id")
    private Produto produto;
    @JoinColumn(name = "quantidade")
    private long quantidade;
    @Column(name = "status")
    private String status;
    @Column(name = "periodo")
    private LocalDate periodo;
    @ManyToOne
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    private Periodo idPeriodo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalDate periodo) {
        this.periodo = periodo;
    }

    public Periodo getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Periodo idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", funcionario=" + funcionario +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", status='" + status + '\'' +
                ", periodo=" + periodo +
                ", idPeriodo=" + idPeriodo +
                '}';
    }
}
