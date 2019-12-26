package br.com.hbsis.item;

import br.com.hbsis.HbApi.invoice.InvoiceItemDTO;
import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.pedido.PedidoService;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final IItemRepository iItemRepository;

    public ItemService(PedidoService pedidoService, ProdutoService produtoService, IItemRepository iItemRepository) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
        this.iItemRepository = iItemRepository;
    }

    public ItemDTO save(ItemDTO itemDTO) {
        this.validate(itemDTO);
        LOGGER.info("Salvando item");
        LOGGER.debug("Item {}", itemDTO);

        Item item = new Item();
        item.setProduto(produtoService.findByProdutoId(itemDTO.getProduto()));
        item.setPedido(pedidoService.findById2(itemDTO.getPedido()));
        item.setQuantidade(itemDTO.getQuantidade());

        item = this.iItemRepository.save(item);

        return ItemDTO.of(item);
    }

    private void validate(ItemDTO itemDTO) {
        LOGGER.info("Validando item");

        if (StringUtils.isEmpty(String.valueOf(itemDTO.getProduto()))) {
            throw new IllegalArgumentException("Favor informar o id do produto");
        }
        if (StringUtils.isEmpty(String.valueOf(itemDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Favor informar a quantidade de itens a comprar");
        }
        if (itemDTO.getProduto() <= 0) {
            throw new IllegalArgumentException("Item não pode ser menor ou igual a 0");
        }
//        this.hbEmplyeeAdminItem(itemDTO);

    }

    public Set<InvoiceItemDTO> findProdutosPedido(Long id) {
        Set<InvoiceItemDTO> invoiceDTOSet = new HashSet<>();
        List<Item> produtoDoPedido = iItemRepository.findFkProd(id);
        for (Item item : produtoDoPedido) {
            invoiceDTOSet.add(new InvoiceItemDTO(item.getProduto().getNomeProduto(), item.getQuantidade()));
        }
        return invoiceDTOSet;
    }

    public ItemDTO findByPedidoAndProduto(long pedido, long produto) {
        Pedido ipedido;
        Produto iproduto;

        ipedido = pedidoService.findById2(pedido);
        iproduto = produtoService.findByProdutoId(produto);

        Optional<Item> optional = this.iItemRepository.findByPedidoAndProduto(ipedido, iproduto);

        if (optional.isPresent()) {
            return ItemDTO.of(optional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s don't exist", iproduto, ipedido));
    }

    public ItemDTO findById(Long id) {
        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            return ItemDTO.of(item);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public ItemDTO update(ItemDTO itemDTO, Long id) {
        Optional<Item> itemOpcional = this.iItemRepository.findById(id);

        if (itemOpcional.isPresent()) {
            Item item = itemOpcional.get();

            LOGGER.info("Atualizando item... id: [{}]", item.getId());
            LOGGER.debug("Payload: {}", itemDTO);

            item.setProduto(produtoService.findByProdutoId(itemDTO.getProduto()));
            item.setPedido(pedidoService.findById2(itemDTO.getPedido()));
            item.setQuantidade(itemDTO.getQuantidade());

            item = this.iItemRepository.save(item);

            return itemDTO.of(item);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para item cujo ID = [{}]", id);

        this.iItemRepository.deleteById(id);
    }
}
