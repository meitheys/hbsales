package br.com.hbsis.produto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository produtoRepository;

    public ProdutoService(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        this.validate(produtoDTO);
        LOGGER.info("Salvando Produto");
        LOGGER.debug("Produto {}", produtoDTO);

        Produto produto = new Produto();
        produto.setCodigo_linha(produtoDTO.getCodigo_linha());
        produto.setCodigo_produto(produtoDTO.getCodigo_produto());
        produto.setNome_produto(produto.getNome_produto());
        produto.setPeso(produtoDTO.getPeso());
        produto.setPreco_produto(produtoDTO.getPreco_produto());
        produto.setUnidade(produtoDTO.getUnidades());
        produto.setValidade(produtoDTO.getValidade());

        produto = this.produtoRepository.save(produto);

        return ProdutoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando produto");

        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo");
        }

    }

    public ProdutoDTO findById(Long codigo_produto) {
        Optional<Produto> produtoDuplicado = this.produtoRepository.findById(codigo_produto);

        if (produtoDuplicado.isPresent()) {
            Produto produto = produtoDuplicado.get();
            return ProdutoDTO.of(produto);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_produto));
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long codigo_produto) {
        Optional<Produto> produtoDuplicadoDois = this.produtoRepository.findById(codigo_produto);

        if (produtoDuplicadoDois.isPresent()) {
            Produto produtoDuplicado = produtoDuplicadoDois.get();

            LOGGER.info("Atualizando Produto... id: [{}]", produtoDTO.getCodigo_produto());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto já existe: {}", produtoDuplicado);

            produtoDuplicado.setValidade(produtoDuplicado.getValidade());
            produtoDuplicado.setUnidade(produtoDuplicado.getUnidade());
            produtoDuplicado.setPreco_produto(produtoDuplicado.getPreco_produto());
            produtoDuplicado.setPeso(produtoDuplicado.getPeso());
            produtoDuplicado.setNome_produto(produtoDuplicado.getNome_produto());
            produtoDuplicado.setCodigo_produto(produtoDuplicado.getCodigo_produto());
            produtoDuplicado.setCodigo_linha(produtoDuplicado.getCodigo_linha());

            produtoDuplicado = this.produtoRepository.save(produtoDuplicado);

            return ProdutoDTO.of(produtoDuplicado);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_produto));
    }

    public void delete(Long codigo_produto) {
        LOGGER.info("Executando delete para produto de ID: [{}]", codigo_produto);

        this.produtoRepository.deleteById(codigo_produto);
    }

}
