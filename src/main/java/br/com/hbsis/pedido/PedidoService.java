package br.com.hbsis.pedido;

import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);
    private IPedidoRepository iPedidoRepository;
    private FuncionarioService funcionarioService;
    private ProdutoService produtoService;

    public PedidoService(IPedidoRepository iPedidoRepository, FuncionarioService funcionarioService, ProdutoService produtoService){
        this.iPedidoRepository = iPedidoRepository;
        this.funcionarioService = funcionarioService;
        this.produtoService = produtoService;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO){
        this.validate(pedidoDTO);

        LOGGER.info("Salvando pedido");
        LOGGER.debug("Pedido {}", pedidoDTO);

        Pedido pedido = new Pedido();
        pedido.setId(pedidoDTO.getId());
        pedido.setProduto(produtoService.findByProdutoId(pedidoDTO.getProdutos()));
        pedido.setQuantidade(pedidoDTO.getQuantidade());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setFuncionario(funcionarioService.findByFuncionarioId(pedidoDTO.getFuncionario()));
        pedido = this.iPedidoRepository.save(pedido);

        System.out.println(pedido);

        return pedidoDTO.of(pedido);
    }

    private void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando pedido");

        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getFuncionario()))) {
            throw new IllegalArgumentException("Favor informar o funcionario");
        }
        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getProdutos()))) {
            throw new IllegalArgumentException("Favor informar o produto");
        }
        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Favor informar a quantidade");
        }
        if(StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatus()))) {
            throw new IllegalArgumentException("Favor informar o status");
        }
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            return PedidoDTO.of(pedido);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoOptionald = this.iPedidoRepository.findById(id);

        if (pedidoOptionald.isPresent()) {
            Pedido pedidoOptional = pedidoOptionald.get();

            LOGGER.info("Atualizando Produto... id: [{}]", pedidoDTO.getId());
            LOGGER.debug("Payload: {}", pedidoDTO);
            LOGGER.debug("Produto já existe: {}", pedidoOptional);

            pedidoOptional.setFuncionario(pedidoOptional.getFuncionario());
            pedidoOptional.setProduto(pedidoOptional.getProduto());
            pedidoOptional.setQuantidade(pedidoOptional.getQuantidade());
            pedidoOptional.setStatus(pedidoOptional.getStatus());
            pedidoOptional = this.iPedidoRepository.save(pedidoOptional);

            return PedidoDTO.of(pedidoOptional);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para pedido de ID: [{}]", id);

        this.iPedidoRepository.deleteById(id);
    }



}
