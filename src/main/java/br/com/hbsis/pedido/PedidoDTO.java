package br.com.hbsis.pedido;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.item.ItemDTO;
import br.com.hbsis.produto.Produto;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

public class PedidoDTO {
    private long id;
    private String codPedido;
    private long funcionario;
    private String status;
    private LocalDate periodo;
    private long idPeriodo;
    private List<ItemDTO> itemDTO;
    private long fornecedor;

    public PedidoDTO() {

    }

    public PedidoDTO(long id, long funcionario, String status, LocalDate periodo, long idPeriodo, String codPedido, long fornecedor) {
        this.id = id;
        this.funcionario = funcionario;
        this.status = status;
        this.periodo = periodo;
        this.idPeriodo = idPeriodo;
        this.codPedido = codPedido;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getFuncionario().getId(),
                pedido.getStatus(),
                pedido.getPeriodo(),
                pedido.getIdPeriodo().getId(),
                pedido.getCodPedido(),
                pedido.getFornecedor().getIdFornecedor()
        );
    }

    public long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(long fornecedor) {
        this.fornecedor = fornecedor;
    }

    public long getIdPeriodo() {

        return idPeriodo;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
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

    public List<ItemDTO> getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(List<ItemDTO> itemDTO) {
        this.itemDTO = itemDTO;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", codPedido='" + codPedido + '\'' +
                ", funcionario=" + funcionario +
                ", status='" + status + '\'' +
                ", periodo=" + periodo +
                ", idPeriodo=" + idPeriodo +
                ", itemDTO=" + itemDTO +
                ", fornecedor=" + fornecedor +
                '}';
    }
}