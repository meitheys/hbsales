package br.com.hbsis.HbApi.invoice;

import br.com.hbsis.item.Item;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceItemDTO {
        private String itemName;
        private long amount;

        public InvoiceItemDTO() {
        }

        public InvoiceItemDTO(String itemName, long amount) {
            this.itemName = itemName;
            this.amount = amount;
        }


    public static List<InvoiceItemDTO> parserToList(List<Item> items) {
            return items.stream().map(item -> new InvoiceItemDTO(item.getProduto().getNomeProduto(), item.getQuantidade())).collect(Collectors.toList());
        }

        public String getItemName() {
            return itemName;
        }

        public long getAmount() {
            return amount;
        }
    }

