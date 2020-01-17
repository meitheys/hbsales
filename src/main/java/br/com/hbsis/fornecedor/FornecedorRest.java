package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fornecedores")
public class FornecedorRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorRest.class);

    private final FornecedorService fornecedorService;
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorRest(FornecedorService fornecedorService, FornecedorRepository fornecedorRepository) {
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;
    }

    @PostMapping("/save")
    public FornecedorDTO save(@RequestBody FornecedorDTO FornecedorDTO) {
        LOGGER.info("Recebendo solicitação de persistência de br.com.hbsis.fornecedor...");
        LOGGER.debug("Payaload: {}", FornecedorDTO);

        return this.fornecedorService.save(FornecedorDTO);
    }

    @GetMapping("/{id}")
    public FornecedorDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.fornecedorService.findById(id);
    }

    @GetMapping("/findFornecedor")
    public List<Fornecedor> findAll() {
        return this.fornecedorRepository.findAll();
    }

    @PutMapping("/{id}")
    public FornecedorDTO udpate(@PathVariable("id") Long id, @RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo Update do Fornecedor de ID: {}", id);
        LOGGER.debug("Payload: {}", fornecedorDTO);

        return this.fornecedorService.update(fornecedorDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete do Fornecedor de ID: {}", id);

        this.fornecedorService.delete(id);
    }
}
