package br.com.hbsis.categoria;

import br.com.hbsis.csv.CategoriaCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categoria")
public class CategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);
    private CategoriaService categoriaService;
    private CategoriaCSV categoriaCSV;

    @Autowired
    public CategoriaRest(CategoriaService categoriaService, CategoriaCSV categoriaCSV) {
        this.categoriaService = categoriaService;
        this.categoriaCSV = categoriaCSV;
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo solicitação de categoria...");
        LOGGER.debug("Payaload: {}", categoriaDTO);

        return this.categoriaService.save(categoriaDTO);
    }

    @GetMapping("/{idcategoria}")
    public Categoria find(@PathVariable("idcategoria") Long idcategoria) {

        LOGGER.info("Recebendo find by ID... id: [{}]", idcategoria);

        return this.categoriaService.findByIdString(idcategoria);
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

    @GetMapping("/exportarcsv")
    public void exportCSV(HttpServletResponse file) throws Exception {
        categoriaCSV.findAll(file);
    }

    @PostMapping("/importarcsv")
    public void importCSV(@RequestParam("file") MultipartFile arquivo) throws Exception {
        categoriaCSV.leitorTotal(arquivo);
    }

}
