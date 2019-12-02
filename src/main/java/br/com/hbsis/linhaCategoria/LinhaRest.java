package br.com.hbsis.linhaCategoria;

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

        @Autowired
        public LinhaRest(LinhaService linhaService) {
            this.linhaService = linhaService;
        }

        @PostMapping
        public LinhaDTO save(@RequestBody LinhaDTO linhaDTO) {
            LOGGER.info("Recebendo solicitação de linha...");
            LOGGER.debug("Payload: {}", linhaDTO);

            return this.linhaService.save(linhaDTO);
        }

        @GetMapping("/{codigo_linha}")
        public LinhaDTO findLinha(@PathVariable("codigo_linha") Long codigo_linha) {

            LOGGER.info("Recebendo find by ID... id: [{}]", codigo_linha);

            return this.linhaService.findLinha(codigo_linha);
        }

        @PutMapping("/{idlinha}")
        public LinhaDTO udpate(@PathVariable("codigo_linha") Long codigo_linha, @RequestBody LinhaDTO linhaDTO) {
            LOGGER.info("Recebendo Update de linha, id: {}", codigo_linha);
            LOGGER.debug("Payload: {}", linhaDTO);

            return this.linhaService.update(linhaDTO, codigo_linha);
        }

        @DeleteMapping("/{codigo_linha}")
        public void delete(@PathVariable("codigo_linha") Long codigo_linha) {
            LOGGER.info("Recebendo Delete da Linha de ID: {}", codigo_linha);

            this.linhaService.delete(codigo_linha);
        }

        //Trabalhando com excel ---------

    @GetMapping("/exportarcsv")
    public void exportCSV(HttpServletResponse file) throws Exception {
        linhaService.findAll(file);
    }

    @PostMapping("/importarcsv")
    public void importCSV(@RequestParam("file") MultipartFile arquivo) throws Exception {
        linhaService.leitorTotal(arquivo);
    }


    }
