package br.com.hbsis.produto;

import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import br.com.hbsis.fornecedor.FornecedorRepository;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaCategoria.ILinhaRepository;
import br.com.hbsis.linhaCategoria.LinhaService;
import br.com.hbsis.validacoes.StringValidations;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private final IProdutoRepository iProdutoRepository;
    private final LinhaService linhaService;
    private final CategoriaService categoriaService;
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorService fornecedorService;
    private final ICategoriaRepository iCategoriaRepository;
    private final ILinhaRepository iLinhaRepository;
    private final StringValidations stringValidations;

    public ProdutoService(IProdutoRepository iProdutoRepository, LinhaService linhaService, CategoriaService categoriaService, FornecedorRepository fornecedorRepository, FornecedorService fornecedorService, ICategoriaRepository iCategoriaRepository, ILinhaRepository iLinhaRepository, ProdutoDTO produtoDTO, StringValidations stringValidations) {
        this.iProdutoRepository = iProdutoRepository;
        this.categoriaService = categoriaService;
        this.linhaService = linhaService;
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorService = fornecedorService;
        this.iCategoriaRepository = iCategoriaRepository;
        this.iLinhaRepository = iLinhaRepository;
        this.stringValidations = stringValidations;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();
        produto.setUnidadePeso(produtoDTO.getUnidadePeso());
        produto.setLinha(linhaService.findById(produtoDTO.getLinha()));
        produto.setCodigoProduto(stringValidations.zeroAEsquerda(produtoDTO.getCodigoProduto().toUpperCase()));
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPeso(produtoDTO.getPeso());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidade(produtoDTO.getUnidades());
        produto.setValidade(produtoDTO.getValidade());

        produto = this.iProdutoRepository.save(produto);

        return produtoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando produto");

        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getValidade()))) {
            throw new IllegalArgumentException("A data de validade do produto está nula");
        }
        if (StringUtils.isEmpty(produtoDTO.getUnidadePeso())) {
            throw new IllegalArgumentException("Unidade de medida não pode ser nula");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getLinha()))) {
            throw new IllegalArgumentException("Linha do produto não informada");
        }
        if (StringUtils.isEmpty(produtoDTO.getCodigoProduto())) {
            throw new IllegalArgumentException("Código do produto não pode ser nulo");
        }
        if (StringUtils.isEmpty(produtoDTO.getNomeProduto())) {
            throw new IllegalArgumentException("O nome do produto nao deve ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getPrecoProduto()))) {
            throw new IllegalArgumentException("O preço não deve ser nulo");
        }
        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getUnidades()))) {
            throw new IllegalArgumentException("A unidade não deve ser nula");
        }

        switch (produtoDTO.getUnidadePeso()) {
            case "Kg":
            case "g":
            case "Mg":
                break;
            default:
                throw new IllegalArgumentException("Informe uma unidade correta. Ela são: Kg, g, Mg.");
        }

        Produto produto = new Produto();
        if (iProdutoRepository.existsById(produto.getIdProduto())) {
            produto.setIdProduto(iProdutoRepository.findById(produto.getIdProduto()).get().getIdProduto());
            update(ProdutoDTO.of(produto), produto.getIdProduto());
        } else {
            LOGGER.info("Produto {} pertence a outro fornecedor", produto.getIdProduto());
        }

    }

    public ProdutoDTO findByCodigoProduto(String codigoProduto) {
        Optional<Produto> produtoDuplicado = this.iProdutoRepository.findByCodigoProduto(codigoProduto);

        if (produtoDuplicado.isPresent()) {
            Produto produto = produtoDuplicado.get();
            return ProdutoDTO.of(produto);
        }


        throw new IllegalArgumentException(String.format("codigoProduto %s não existe", codigoProduto));
    }

    public ProdutoDTO findById(Long codigo_produto) {
        Optional<Produto> produtoDuplicado = this.iProdutoRepository.findById(codigo_produto);

        if (produtoDuplicado.isPresent()) {
            Produto produto = produtoDuplicado.get();
            return ProdutoDTO.of(produto);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_produto));
    }

    public Produto findByProdutoId(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long codigo_produto) {
        this.validate(produtoDTO);
        Optional<Produto> produtoDuplicadoDois = this.iProdutoRepository.findById(codigo_produto);

        if (produtoDuplicadoDois.isPresent()) {
            Produto produtoDuplicado = produtoDuplicadoDois.get();

            LOGGER.info("Atualizando Produto... id: [{}]", produtoDTO.getCodigoProduto());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto já existe: {}", produtoDuplicado);

            produtoDuplicado.setValidade(produtoDuplicado.getValidade());
            produtoDuplicado.setUnidade(produtoDuplicado.getUnidade());
            produtoDuplicado.setPrecoProduto(produtoDuplicado.getPrecoProduto());
            produtoDuplicado.setPeso(produtoDuplicado.getPeso());
            produtoDuplicado.setNomeProduto(produtoDuplicado.getNomeProduto());
            produtoDuplicado.setCodigoProduto(produtoDuplicado.getCodigoProduto());
            produtoDuplicado.setLinha(produtoDuplicado.getLinha());

            produtoDuplicado = this.iProdutoRepository.save(produtoDuplicado);

            return ProdutoDTO.of(produtoDuplicado);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_produto));
    }

    public void delete(Long codigo_produto) {
        LOGGER.info("Executando delete para produto de ID: [{}]", codigo_produto);

        this.iProdutoRepository.deleteById(codigo_produto);
    }
}




