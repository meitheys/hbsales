package br.com.hbsis.HbApi.invoice;

import br.com.hbsis.item.Item;

import java.util.List;
import java.util.Set;

public class InvoiceDTO {
    private String cnpjFornecedor;
    private String employeeUuid;
    private List<InvoiceItemDTO> invoiceItemDTOSet;
    private double totalValue;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String cnpjFornecedor, String employeeUuid, List<InvoiceItemDTO> invoiceItemDTOSet, double totalValue) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.invoiceItemDTOSet = invoiceItemDTOSet;
        this.totalValue = totalValue;
    }

    public static InvoiceDTO parser(String cnpjFornecedor, String uuid, List<Item> items, double totalValue) {
        return new InvoiceDTO(cnpjFornecedor, uuid, InvoiceItemDTO.parserToList(items), totalValue);
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public List<InvoiceItemDTO> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public double getTotalValue() {
        return totalValue;
    }
}
