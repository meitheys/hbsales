package br.com.hbsis.linhaCategoria;

import br.com.hbsis.csv.LinhaCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/linha")
public class LinhaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaRest.class);
    private LinhaService linhaService;
    private LinhaCSV linhaCSV;

    @Autowired
    public LinhaRest(LinhaService linhaService, LinhaCSV linhaCSV) {
        this.linhaService = linhaService;
        this.linhaCSV = linhaCSV;
    }

    @PostMapping
    public LinhaDTO save(@RequestBody LinhaDTO linhaDTO) {
        LOGGER.info("Recebendo solicitação de linha...");
        LOGGER.debug("Payload: {}", linhaDTO);

        return this.linhaService.save(linhaDTO);
    }

    @GetMapping("/{id}")
    public LinhaDTO findLinha(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.linhaService.findLinha(id);
    }

    @PutMapping("/{id}")
    public LinhaDTO udpate(@PathVariable("id") Long id, @RequestBody LinhaDTO linhaDTO) {
        LOGGER.info("Recebendo Update de linha, id: {}", id);
        LOGGER.debug("Payload: {}", linhaDTO);

        return this.linhaService.update(linhaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete da Linha de ID: {}", id);

        this.linhaService.delete(id);
    }

    @GetMapping("/exportarcsv")
    public void exportCSV(HttpServletResponse file) throws Exception {
        linhaCSV.findAll(file);
    }

    @PostMapping("/importarcsv")
    public void importCSV(@RequestParam("file") MultipartFile arquivo) throws Exception {
        linhaCSV.leitorTotal(arquivo);
    }
}
