package br.com.hbsis.periodo;

import br.com.hbsis.pedido.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/periodo")
public class PeriodoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoRest.class);
    private final PeriodoService periodoService;

    @Autowired
    public PeriodoRest(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    @PostMapping
    public PeriodoDTO save(@RequestBody PeriodoDTO periodoDTO) {
        LOGGER.info("Recebendo save de periodo...");
        LOGGER.debug("Payload: {}", periodoDTO);
        return this.periodoService.save(periodoDTO);
    }

    @GetMapping("/{id}")
    public PeriodoDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo id... id: [{}]", id);

        return this.periodoService.findById(id);
    }

    @PutMapping("/{id}")
    public PeriodoDTO update(@PathVariable("id") Long id, @RequestBody PeriodoDTO periodoDTO) {
        LOGGER.info("Alterando periodo de id: {}", id);
        LOGGER.info("Payload: {}", periodoDTO);

        return this.periodoService.update(periodoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Deletando periodo de id: {}", id);

        this.periodoService.delete(id);
    }





}
