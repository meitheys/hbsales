package br.com.hbsis.HbApi.invoice;

import br.com.hbsis.item.Item;

import java.util.Set;

public class InvoiceDTO {

    private String cnpjFornecedor;
    private String employeeUuid;
    private Set<InvoiceItemDTO> invoiceItemDTOSet;
    private double totalValue;

    public InvoiceDTO(){}

    public InvoiceDTO(String cnpjFornecedor, String employeeUuid, Set<InvoiceItemDTO> invoiceItemDTOSet, double totalValue) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.invoiceItemDTOSet = invoiceItemDTOSet;
        this.totalValue = totalValue;
    }

    public static InvoiceDTO parser(String cnpjFornecedor, String uuid, Set<Item> items, double totalValue) {
        return new InvoiceDTO(cnpjFornecedor, uuid, InvoiceItemDTO.parseList(items), totalValue);
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public Set<InvoiceItemDTO> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(Set<InvoiceItemDTO> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "cnpjFornecedor='" + cnpjFornecedor + '\'' +
                ", employeeUuid='" + employeeUuid + '\'' +
                ", invoiceItemDTOSet=" + invoiceItemDTOSet +
                ", totalValue=" + totalValue +
                '}';
    }
}
