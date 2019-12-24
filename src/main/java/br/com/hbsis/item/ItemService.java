package br.com.hbsis.item;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.pedido.PedidoService;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

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
        item.setIdProduto(produtoService.findByProdutoId(itemDTO.getIdProduto()));
        item.setQuantidade(itemDTO.getQuantidade());
        item.setItemName(itemDTO.getItemName());

        item = this.iItemRepository.save(item);

        return ItemDTO.of(item);
    }

    private void validate(ItemDTO itemDTO) {
        LOGGER.info("Validando item");

        if(StringUtils.isEmpty(String.valueOf(itemDTO.getIdProduto()))) {
            throw new IllegalArgumentException("Favor informar o id do produto");
        }
        if(StringUtils.isEmpty(String.valueOf(itemDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Favor informar a quantidade de itens a comprar");
        }
        if(itemDTO.getIdProduto() <= 0) {
            throw new IllegalArgumentException("Item não pode ser menor ou igual a 0");
        }
//        this.hbEmplyeeAdminItem(itemDTO);

    }

    public ItemDTO findById(Long id) {
        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            return ItemDTO.of(item);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

//    private void hbEmplyeeAdminItem(ItemDTO itemDTO) {
//        RestTemplate template = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "f59fec50-1b67-11ea-978f-2e728ce88125");
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        HttpEntity httpEntity = new HttpEntity(itemDTO, headers);
//        ResponseEntity<InvoiceItemDTO> response = template.exchange("http://10.2.54.25:9999/api/invoice", HttpMethod.POST, httpEntity, InvoiceItemDTO.class);
//        itemDTO.setQuantidade(Objects.requireNonNull(response.getBody()).getAmount());
//        itemDTO.setItemName(response.getBody().getItemName());
//    }

    public ItemDTO update(ItemDTO itemDTO, Long id) {
        Optional<Item> itemOpcional = this.iItemRepository.findById(id);

        if (itemOpcional.isPresent()) {
            Item item = itemOpcional.get();

            LOGGER.info("Atualizando item... id: [{}]", item.getId());
            LOGGER.debug("Payload: {}", itemDTO);

            item.setIdProduto(produtoService.findByProdutoId(itemDTO.getIdProduto()));
            item.setQuantidade(itemDTO.getQuantidade());
            item.setItemName(itemDTO.getItemName());

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
