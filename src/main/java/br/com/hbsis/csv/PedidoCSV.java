package br.com.hbsis.csv;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.pedido.IPedidoRepository;
import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.pedido.PedidoDTO;
import br.com.hbsis.pedido.PedidoService;
import br.com.hbsis.periodo.Periodo;
import br.com.hbsis.periodo.PeriodoService;
import br.com.hbsis.produto.ProdutoService;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoCSV {

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoCSV.class);
    private IPedidoRepository iPedidoRepository;
    private FuncionarioService funcionarioService;
    private ProdutoService produtoService;
    private PeriodoService periodoService;

    public PedidoCSV(IPedidoRepository iPedidoRepository, FuncionarioService funcionarioService, ProdutoService produtoService, PeriodoService periodoService) {
        this.iPedidoRepository = iPedidoRepository;
        this.funcionarioService = funcionarioService;
        this.produtoService = produtoService;
        this.periodoService = periodoService;
    }

    public void findFornecedor(HttpServletResponse httpservletresponse, Long id) {
        try {
            String file = "pedidosPorFornecedor.csv";

            httpservletresponse.setContentType("text/csv");

            httpservletresponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"");

            PrintWriter printwriter = httpservletresponse.getWriter();

            ICSVWriter icsvwriter = new CSVWriterBuilder(printwriter).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();

            String headCVS[] = {"nome_produto", "quantidade", "razao"};

            icsvwriter.writeNext(headCVS);

            Periodo periodo;
            periodo = periodoService.findByPeriodoId(id);

            List<Pedido> pedidos;

            pedidos = iPedidoRepository.findByPeriodo(periodo);

            for (Pedido pedido : pedidos) {
                icsvwriter.writeNext(new String[]{

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findAllPeriodoVendas(HttpServletResponse httpservletresponse, Long id) {
        try {
            String file = "pedidosPorFuncionario.csv";

            httpservletresponse.setContentType("text/csv");

            httpservletresponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"");

            PrintWriter printwriter = httpservletresponse.getWriter();

            ICSVWriter icsvwriter = new CSVWriterBuilder(printwriter).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();

            String headCVS[] = {"funcionario", "produto", "quantidade", "razao/cnpj"};

            icsvwriter.writeNext(headCVS);

            Funcionario funcionario;
            funcionario = funcionarioService.findByFuncionarioId(id);

            List<Pedido> pedidos;

            pedidos = iPedidoRepository.findByFuncionario(funcionario);

            for (Pedido pedido : pedidos) {
                icsvwriter.writeNext(new String[]{
                        pedido.getFuncionario().getNomeFuncionario(),
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PedidoDTO> findAllByFornecedorId(Long id) {
        LOGGER.info("Buscando pedidos do funcionario: [{}]", id);

        //Listando pedido por findAll no Repository, o find all no repositorio está botando tudo em uma list
        List<Pedido> listaPedido = this.iPedidoRepository.findAllByFuncionarioId(id);

        //Bota o PedidoDTO em uma array
        List<PedidoDTO> ListaPedidoDTO = new ArrayList<>();

        //FOR EACH
        PedidoDTO pedidoDTO = new PedidoDTO();
        if (pedidoDTO.getStatus() != "Cancelado") {
            for (Pedido pedido : listaPedido) {
                ListaPedidoDTO.add(PedidoDTO.of(pedido));
                LOGGER.info("Pedidos");
            }

        }
        return ListaPedidoDTO;
    }
}
