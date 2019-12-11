package br.com.hbsis.produto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/produto")
public class ProdutoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoRest(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PutMapping("/importarPF/{codigo_produto}")
    public void acharFornecedorProduto(@PathVariable("codigo_produto") Long codigo_produto, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        LOGGER.info("Adicionando produto a fornecedor de ID... [{}]", codigo_produto);
        produtoService.acharFornecedorProduto(codigo_produto, multipartFile);
    }


    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo solicitação de persistência de br.com.hbsis.fornecedor...");
        LOGGER.debug("Payaload: {}", produtoDTO);

        return this.produtoService.save(produtoDTO);
    }

    @GetMapping("/{codigo_produto}")
    public ProdutoDTO find(@PathVariable("codigo_produto") Long codigo_produto) {

        LOGGER.info("Recebendo find by ID... id: [{}]", codigo_produto);

        return this.produtoService.findById(codigo_produto);
    }

    @PutMapping("/{codigo_produto}")
    public ProdutoDTO udpate(@PathVariable("codigo_produto") Long codigo_produto, @RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo Update do Produto de ID: {}", codigo_produto);
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.update(produtoDTO, codigo_produto);
    }

    @DeleteMapping("/{codigo_produto}")
    public void delete(@PathVariable("codigo_produto") Long codigo_produto) {
        LOGGER.info("Recebendo Delete do Produto de ID: {}", codigo_produto);

        this.produtoService.delete(codigo_produto);
    }

    //Excel

   /* @GetMapping("/exportarcsv")
    public void exportCSV(HttpServletResponse file) throws Exception {
        produtoService.findAll(file);
    }

    @PostMapping("/importarcsv")
    public void importCSV(@RequestParam("file") MultipartFile arquivo) throws Exception {
        produtoService.leitorTotal(arquivo);
    }*/



}
