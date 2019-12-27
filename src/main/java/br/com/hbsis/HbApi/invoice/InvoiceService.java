package br.com.hbsis.HbApi.invoice;

import br.com.hbsis.HbApi.HbApiService;
import br.com.hbsis.item.ItemService;
import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.pedido.PedidoService;
import br.com.hbsis.produto.ProdutoService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Service
public class InvoiceService {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final ItemService itemService;
    private final HbApiService hbApiService;
    private static final Logger LOGGER = LoggerFactory.getLogger(br.com.hbsis.item.ItemService.class);

    public InvoiceService(PedidoService pedidoService, ProdutoService produtoService, ItemService itemService, HbApiService hbApiService) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
        this.itemService = itemService;
        this.hbApiService = hbApiService;
    }

    public InvoiceDTO sendInvoiceToApi(Long id) throws IOException {
        Pedido pedido = pedidoService.findById2(id);
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceItemDTOSet(itemService.findProdutosPedido(id));
        invoiceDTO.setEmployeeUuid(pedido.getUuid());
        invoiceDTO.setCnpjFornecedor(pedido.getFornecedor().getCnpj());
        invoiceDTO.setTotalValue((Double) itemService.pedidoTotal(id));
        hbApiService.hbInvoice(invoiceDTO);

        return invoiceDTO;
    }
}
