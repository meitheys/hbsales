package br.com.hbsis.funcionario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/funcionario")
public class FuncionarioRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioRest.class);
    private FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO) throws IOException {
        LOGGER.info("Recebendo save de funcionario...");
        LOGGER.debug("Payload: {}", funcionarioDTO);

        return this.funcionarioService.save(funcionarioDTO);
    }

    @GetMapping("/{codigo_linha}")
    public FuncionarioDTO findFuncionario(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.funcionarioService.findFuncionario(id);
    }

    @PutMapping("/{idlinha}")
    public FuncionarioDTO udpate(@PathVariable("id") Long id, @RequestBody FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Recebendo Update de funcionario, id: {}", id);
        LOGGER.debug("Payload: {}", funcionarioDTO);

        return this.funcionarioService.update(funcionarioDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete da funcionario de ID: {}", id);

        this.funcionarioService.delete(id);
    }

}
