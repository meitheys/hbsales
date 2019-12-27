package br.com.hbsis.pedido;

import br.com.hbsis.csv.PedidoCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoRest.class);
    private final PedidoService pedidoService;
    private final PedidoCSV pedidoCSV;

    @Autowired
    public PedidoRest(PedidoService periodoService, PedidoCSV pedidoCSV) {
        this.pedidoService = periodoService;
        this.pedidoCSV = pedidoCSV;
    }

    @PostMapping
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO) {
        LOGGER.info("Salvando pedido...");
        LOGGER.debug("Payload: {}", pedidoDTO);
        return this.pedidoService.save(pedidoDTO);
    }

    @GetMapping("/{id}")
    public PedidoDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo id... id: [{}]", id);

        return this.pedidoService.findById(id);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable("id") Long id, @RequestBody PedidoDTO pedidoDTO) {
        return this.pedidoService.update(pedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Deletando pedido com id: {}", id);

        this.pedidoService.delete(id);
    }

    @GetMapping("/exportPorFornecedor/{id}")
    public void exportFornecedorCSV(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) {
        pedidoCSV.findFornecedor(httpServletResponse, id);
    }

    @GetMapping("/exportPorFuncionario/{id}")
    public void exportFuncionarioCSV(HttpServletResponse httpServletResponse, @PathVariable("id") Long id) {
        pedidoCSV.findAllPeriodoVendas(httpServletResponse, id);
    }

    @GetMapping("/funcionario/{id}")
    public List<PedidoDTO> findAll(@PathVariable Long id) {
        return this.pedidoCSV.findAllByFornecedorId(id);
    }

    @PutMapping("/cancelar/{id}")
    public void cancelar(@PathVariable("id") Long id) {
        LOGGER.info("Cancelando...");
        this.pedidoService.cancelar(id);
    }

    @PutMapping("/retirar/{id}")
    public void retirar(@PathVariable("id") Long id) {
        LOGGER.info("Retirando...");
        this.pedidoService.retirar(id);
    }

}
