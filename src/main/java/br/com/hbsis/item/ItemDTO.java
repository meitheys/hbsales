package br.com.hbsis.item;

public class ItemDTO {

    private long id;
    private long idProduto;
    private long quantidade;
    private String itemName;

    public ItemDTO(){}

    public ItemDTO(long id, long idProduto, long quantidade, String itemName) {
        this.id = id;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.itemName = itemName;
    }

    public static ItemDTO of(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getIdProduto().getIdProduto(),
                item.getQuantidade(),
                item.getItemName()
        );
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
