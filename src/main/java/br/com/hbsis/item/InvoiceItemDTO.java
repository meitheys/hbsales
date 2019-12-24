package br.com.hbsis.item;

import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceItemDTO {

    private int amount;
    private String itemName;

    public InvoiceItemDTO(String nomeProduto, long quantidade) {
        this.itemName = itemName;
        this.amount = amount;
    }

    public static Set<InvoiceItemDTO> parseList(Set<Item> items) {
        return items.stream().map(item -> new InvoiceItemDTO(item.getIdProduto().getNomeProduto(), item.getQuantidade())).collect(Collectors.toSet());
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
