package br.com.hbsis.pedido;

import br.com.hbsis.HbApi.invoice.InvoiceDTO;
import br.com.hbsis.HbApi.invoice.InvoiceItemDTO;
import br.com.hbsis.email.Email;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.item.Item;
import br.com.hbsis.item.ItemDTO;
import br.com.hbsis.item.ItemService;
import br.com.hbsis.periodo.PeriodoService;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);
    private IPedidoRepository iPedidoRepository;
    private FuncionarioService funcionarioService;
    private ProdutoService produtoService;
    private PeriodoService periodoService;
    private ItemService itemService;
    private FornecedorService fornecedorService;
    private Email email;

    @Autowired
    private JavaMailSender mailSender;

    public PedidoService(IPedidoRepository iPedidoRepository, FuncionarioService funcionarioService, ProdutoService produtoService, PeriodoService periodoService, @Lazy ItemService itemService, FornecedorService fornecedorService, Email email) {
        this.iPedidoRepository = iPedidoRepository;
        this.funcionarioService = funcionarioService;
        this.produtoService = produtoService;
        this.periodoService = periodoService;
        this.itemService = itemService;
        this.fornecedorService = fornecedorService;
        this.email = email;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO){
        this.validate(pedidoDTO);

        LOGGER.info("Salvando pedido");
        LOGGER.debug("Pedido {}", pedidoDTO);

        Email email = new Email();
        Pedido pedido = new Pedido();
        List<Item> listaDeitens = new ArrayList<>();

        Funcionario funcionario = funcionarioService.findByFuncionarioId(pedidoDTO.getFuncionario());
        Fornecedor fornecedor = fornecedorService.findByFornecedorId(pedidoDTO.getFornecedor());

        pedido.setFuncionario(funcionario);
        pedido.setFornecedor(fornecedor);
        pedido.setUuid(funcionario.getUuid());
        pedido.setCodPedido(pedidoDTO.getCodPedido());
        pedido.setPeriodo(LocalDate.now());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setIdPeriodo(periodoService.findByPeriodoId(pedidoDTO.getIdPeriodo()));

        pedido = this.iPedidoRepository.save(pedido);

        if(validateInvoice(pedido.getIdPeriodo().getIdFornecedor().getCnpj(), pedido.getFuncionario().getUuid(), parseItens(pedidoDTO.getItemDTO(), pedido), total(pedidoDTO.getItemDTO()))){

        for (ItemDTO itemDTO : pedidoDTO.getItemDTO()) {
            Item item = new Item();
            itemDTO.setPedido(pedido.getId());
            itemService.save(itemDTO);
            item.setProduto(produtoService.findByProdutoId(itemDTO.getProduto()));
            item.setQuantidade(itemDTO.getQuantidade());
            listaDeitens.add(item);
        }
        this.email.enviar(pedido);
        }else{
            throw new IllegalArgumentException("Validação com API falhou");
        }
        return pedidoDTO.of(pedido);
    }

    public double total(List<ItemDTO> itens){
        double somaTotal = 0;
        for (ItemDTO item : itens){
            Produto produto = produtoService.findByProdutoId(item.getProduto());
            somaTotal += (produto.getPrecoProduto() * item.getQuantidade());
        }
        return somaTotal;
    }

    private void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando pedido");

        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getFuncionario()))) {
            throw new IllegalArgumentException("Favor informar o funcionario");
        }
        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatus()))) {
            throw new IllegalArgumentException("Favor informar o status");
        }
    }

    public List<Item> parseItens(List<ItemDTO> itemDTOS, Pedido pedido){
        List<Item> items = new ArrayList<>();
        for (ItemDTO itensDTO : itemDTOS){
            Item itemClasse = new Item();
            itemClasse.setPedido(pedido);
            itemClasse.setQuantidade(itensDTO.getQuantidade());
            itemClasse.setProduto(produtoService.findByProdutoId(itensDTO.getProduto()));
            items.add(itemClasse);
        }
        return items;
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            return PedidoDTO.of(pedido);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Pedido findById2(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            return pedidoOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void validarUpdate(PedidoDTO pedidoDTO, Long id){
        Pedido pedido = new Pedido();
        pedido = findById2(id);

        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getFuncionario()))) {
            throw new IllegalArgumentException("Favor informar o funcionario");
        }
        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatus()))) {
            throw new IllegalArgumentException("Favor informar o status");
        }
        if (!pedido.getStatus().equals("Ativo")) {
            throw new IllegalArgumentException("Este pedido já foi cancelado/retirado!");
        }
    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        this.validate(pedidoDTO);
        Optional<Pedido> optionalPeriodoVendas = this.iPedidoRepository.findById(id);
        this.validarUpdate(pedidoDTO, id);

        if (optionalPeriodoVendas.isPresent()) {
            Pedido pedido = optionalPeriodoVendas.get();

            LOGGER.info("Updating PeriodoVendas... id: [{}]", pedido.getId());
            LOGGER.debug("Payload: {}", pedidoDTO);

            pedido.setFuncionario(funcionarioService.findByFuncionarioId(pedidoDTO.getFuncionario()));
            pedido.setStatus(pedidoDTO.getStatus());
            pedido.setPeriodo(LocalDate.now());
            pedido.setIdPeriodo(periodoService.findByPeriodoId(pedidoDTO.getIdPeriodo()));

            pedido = this.iPedidoRepository.save(pedido);

            return pedidoDTO.of(pedido);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void cancelar(Long id){
        LOGGER.info("Cancelando pedido, id: [{}]", id);
        Pedido pedido = this.findById2(id);

        if(pedido.getStatus().equalsIgnoreCase("Ativo")) {
            pedido.setStatus("Cancelado");
            this.iPedidoRepository.save(pedido);
            LOGGER.info("Cancelado!");
        }
    }

    public void retirar(Long id){
        LOGGER.info("Retirando pedido, id: [{}]", id);
        Pedido pedido = this.findById2(id);

            pedido.setStatus("Retirado");
            this.iPedidoRepository.save(pedido);
            LOGGER.info("Retirado!");
        }

    public static boolean validateInvoice(String cnpjFornecedor, String uuid, List<Item> items, double totalValue) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<InvoiceDTO> entity = new HttpEntity(InvoiceDTO.parser(cnpjFornecedor, uuid, items, totalValue));

        ResponseEntity<InvoiceDTO> responseEntity = restTemplate.exchange("http://10.2.54.25:9999/v2/api-docs", HttpMethod.POST, entity, InvoiceDTO.class);
        if (responseEntity.getStatusCodeValue() == 200 || responseEntity.getStatusCodeValue() == 201) {
            return true;
        }
        throw new IllegalArgumentException("Inválido. Status: [" + responseEntity.getStatusCodeValue() + "]");
    }
}

