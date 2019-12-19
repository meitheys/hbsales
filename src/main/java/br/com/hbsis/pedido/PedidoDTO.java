package br.com.hbsis.pedido;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.produto.Produto;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class PedidoDTO {
    private long id;
    private long funcionario;
    private long produtos;
    private long quantidade;
    private String status;
    private LocalDate periodo;
    private long idPeriodo;

    public PedidoDTO(){

    }

    public PedidoDTO(long id, long funcionario, long produtos, long quantidade, String status, LocalDate periodo, long idPeriodo){
        this.id = id;
        this.funcionario = funcionario;
        this.produtos = produtos;
        this.quantidade = quantidade;
        this.status = status;
        this.periodo = periodo;
        this.idPeriodo = idPeriodo;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getFuncionario().getId(),
                pedido.getProduto().getIdProduto(),
                pedido.getQuantidade(),
                pedido.getStatus(),
                pedido.getPeriodo(),
                pedido.getIdPeriodo().getId()
        );
    }

    public long getIdPeriodo() {

        return idPeriodo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(long funcionario) {
        this.funcionario = funcionario;
    }

    public long getProdutos() {
        return produtos;
    }

    public void setProdutos(long produtos) {
        this.produtos = produtos;
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

    public void setIdPeriodo(long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", funcionario=" + funcionario +
                ", produtos=" + produtos +
                ", quantidade=" + quantidade +
                ", status='" + status + '\'' +
                ", periodo=" + periodo +
                ", idPeriodo=" + idPeriodo +
                '}';
    }
}
