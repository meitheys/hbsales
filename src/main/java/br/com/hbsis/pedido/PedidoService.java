package br.com.hbsis.pedido;

import br.com.hbsis.email.Email;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.item.Item;
import br.com.hbsis.item.ItemDTO;
import br.com.hbsis.item.ItemService;
import br.com.hbsis.periodo.Periodo;
import br.com.hbsis.periodo.PeriodoService;
import br.com.hbsis.produto.ProdutoService;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);
    private IPedidoRepository iPedidoRepository;
    private FuncionarioService funcionarioService;
    private ProdutoService produtoService;
    private PeriodoService periodoService;
    private ItemService itemService;

    @Autowired
    private JavaMailSender mailSender;

    public PedidoService(IPedidoRepository iPedidoRepository, FuncionarioService funcionarioService, ProdutoService produtoService, PeriodoService periodoService, @Lazy  ItemService itemService) {
        this.iPedidoRepository = iPedidoRepository;
        this.funcionarioService = funcionarioService;
        this.produtoService = produtoService;
        this.periodoService = periodoService;
        this.itemService = itemService;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO){
        this.validate(pedidoDTO);

        LOGGER.info("Salvando pedido");
        LOGGER.debug("Pedido {}", pedidoDTO);

        Email email = new Email();
        Pedido pedido = new Pedido();
        List<Item> listaDeitens = new ArrayList<>();

        pedido.setCodPedido(pedidoDTO.getCodPedido());
        pedido.setPeriodo(LocalDate.now());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setFuncionario(funcionarioService.findByFuncionarioId(pedidoDTO.getFuncionario()));
        pedido.setIdPeriodo(periodoService.findByPeriodoId(pedidoDTO.getIdPeriodo()));

        pedido = this.iPedidoRepository.save(pedido);

        for (ItemDTO itemDTO : pedidoDTO.getItemDTO()) {
            Item item = new Item();
            itemDTO.setPedido(pedido.getId());
            itemService.save(itemDTO);
            item.setProduto(produtoService.findByProdutoId(itemDTO.getProduto()));
            item.setQuantidade(itemDTO.getQuantidade());
            listaDeitens.add(item);
        }
//        email.enviar(pedido, listaDeitens);

        return pedidoDTO.of(pedido);
    }

    public String enviar(Pedido pedido) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Compra feita! =)");
        message.setText("Bom dia caro " + pedido.getFuncionario().getNomeFuncionario() + "\r\n"
                + " Você comprou " + " e a data de retirada será em " + pedido.getIdPeriodo().getRetirada()
                + "\r\n"
                + "\r\n"
                + "HBSIS - Soluções em TI" + "\r\n"
                + "Rua Theodoro Holtrup, 982 - Vila Nova, Blumenau - SC"
                + "(47) 2123-5400");

        message.setTo(pedido.getFuncionario().getEmail());
        message.setFrom("math.furtadonn1ptv@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

    public String enviarAtualizacao(Pedido pedido) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Pedido Atualizado! =)");
        message.setText("Bom dia caro " + pedido.getFuncionario().getNomeFuncionario() + "\r\n"
                + " Seu pedido " + ", gostariamos de lembrar que a sua data de retirada será em " + pedido.getIdPeriodo().getRetirada()
                + "\r\n"
                + "\r\n"
                + "HBSIS - Soluções em TI" + "\r\n"
                + "Rua Theodoro Holtrup, 982 - Vila Nova, Blumenau - SC"
                + "(47) 2123-5400");

        message.setTo(pedido.getFuncionario().getEmail());
        message.setFrom("math.furtadonn1ptv@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }

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
            LOGGER.info("Cancelado!");
        }

    public void delete(Long id) {
        LOGGER.info("Executando delete para pedido de ID: [{}]", id);

        this.iPedidoRepository.deleteById(id);
    }

}

