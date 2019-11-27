package br.com.hbsis.categoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
public class CategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaRest(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo solicitação de persistência de br.com.hbsis.categoria...");
        LOGGER.debug("Payaload: {}", categoriaDTO);

        return this.categoriaService.save(categoriaDTO);
    }

    @GetMapping("/{idcategoria}")
    public CategoriaDTO find(@PathVariable("idcategoria") Long idcategoria) {

        LOGGER.info("Recebendo find by ID... id: [{}]", idcategoria);

        return this.categoriaService.findById(idcategoria);
    }

    @PutMapping("/{idcategoria}")
    public CategoriaDTO udpate(@PathVariable("idcategoria") Long idcategoria, @RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo Update da categoria, id: {}", idcategoria);
        LOGGER.debug("Payload: {}", categoriaDTO);

        return this.categoriaService.update(categoriaDTO, idcategoria);
    }

    @DeleteMapping("/{idcategoria}")
    public void delete(@PathVariable("idcategoria") Long idcategoria) {
        LOGGER.info("Recebendo Delete da Categoria de ID: {}", idcategoria);

        this.categoriaService.delete(idcategoria);
    }


}
