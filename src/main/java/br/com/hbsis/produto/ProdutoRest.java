package br.com.hbsis.produto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoRest(ProdutoService produtoService) {
        this.produtoService = produtoService;
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

}
