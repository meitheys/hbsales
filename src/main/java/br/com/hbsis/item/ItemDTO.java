package br.com.hbsis.item;

public class ItemDTO {

    private long id;
    private long produto;
    private long pedido;
    private long quantidade;

    public ItemDTO(){}

    public ItemDTO(long id, long idProduto, long quantidade, long pedido) {
        this.id = id;
        this.pedido = pedido;
        this.produto = idProduto;
        this.quantidade = quantidade;
    }

    public static ItemDTO of(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getProduto().getIdProduto(),
                item.getQuantidade(),
                item.getPedido().getId()
        );
    }

    public long getPedido() {
        return pedido;
    }

    public void setPedido(long pedido) {
        this.pedido = pedido;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduto() {
        return produto;
    }

    public void setProduto(long produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", idProduto=" + produto +
                ", pedido=" + pedido +
                ", quantidade=" + quantidade +
                '}';
    }
}
