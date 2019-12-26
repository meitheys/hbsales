package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;

import br.com.hbsis.validacoes.StringValidations;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private ICategoriaRepository iCategoriaRepository;
    private final FornecedorService fornecedorService;
    private final StringValidations stringValidations;

    public CategoriaService(ICategoriaRepository iCategoriaRepository, FornecedorService fornecedorService, StringValidations stringValidations) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
        this.stringValidations = stringValidations;
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        this.validate(categoriaDTO);

        Fornecedor fornecedorDTO = fornecedorService.findByFornecedorId(categoriaDTO.getFornecedor());

        LOGGER.info("Salvando categoria...");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();

        //Formadores do codigo Categoria
        String codigo = categoriaDTO.getCodigo_categoria();
        String cnpjota = fornecedorDTO.getCnpj();
        String codigoProcessed = stringValidations.codigoValidar(codigo);
        String cnpjProcessed = stringValidations.quatroCNPJ(cnpjota);
        String codigoFixo = "CAT";

        String fim = codigoFixo + cnpjProcessed + codigoProcessed;

        categoria.setCodigoCategoria(fim);
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(fornecedorService.findByFornecedorId(categoriaDTO.getFornecedor()));

        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);
    }

    private void validate(CategoriaDTO categoriaDTO) {
        LOGGER.info("Validando Categoria");

        if (categoriaDTO == null) {
            throw new IllegalArgumentException("!!Categoria Nula!!");
        }
        if (categoriaDTO.getFornecedor() == null) {
            System.out.println("!!Fornecedor está vazio!!");
        } else {
            System.out.println("Fornecedor: " + categoriaDTO.getFornecedor());
        }
        if (StringUtils.isEmpty(categoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Categoria não deve ser vazio!!");
        }
        if (StringUtils.isEmpty(categoriaDTO.getCodigo_categoria())) {
            throw new IllegalArgumentException("Codigo da Categoria não deve ser vazio!!");
        }
    }

    public Categoria findByIdString(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID não existe", id));
    }

    public  Categoria findByCodigoCategoria(String codigoCategoria) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findByCodigoCategoria(codigoCategoria);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("codigoCategoria não existe", codigoCategoria));
    }

    public  Categoria existsByCategoriaLinha(String categoriaLinha) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findByCodigoCategoria(categoriaLinha);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("categoria_linha não existe", categoriaLinha));
    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando categoria... id: [{}]", categoriaExistente.getCodigoCategoria());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Categoria já existe: {}", categoriaExistente);

            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());
            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return CategoriaDTO.of(categoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para categoria, id: [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }
}
