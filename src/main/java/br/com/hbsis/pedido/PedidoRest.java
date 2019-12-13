package br.com.hbsis.pedido;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoRest.class);
    private final PedidoService pedidoService;

    @Autowired
    public PedidoRest(PedidoService periodoService) {
        this.pedidoService = periodoService;
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
        LOGGER.info("Alterando pedido com id: {}", id);
        LOGGER.info("Payload: {}", pedidoDTO);

        return this.pedidoService.update(pedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Deletando pedido com id: {}", id);

        this.pedidoService.delete(id);
    }

}
