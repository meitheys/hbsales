package br.com.hbsis.fornecedor;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;
    private final CategoriaService categoriaService;

    public FornecedorService(FornecedorRepository fornecedorRepository, CategoriaService categoriaService) {
        this.fornecedorRepository = fornecedorRepository;
        this.categoriaService = categoriaService;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        this.validate(fornecedorDTO);
        LOGGER.info("Salvando Fornecedor");
        LOGGER.debug("Fornecedor {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazao(fornecedorDTO.getRazao());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());


        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        fornecedor = this.fornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Validando Usuario");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("Fornecedor não deve ser nulo");
        }

        if (!(StringUtils.isNumeric(fornecedorDTO.getCnpj()))){
            throw  new IllegalArgumentException("Cnpj não deve conter letras somente números");
        }

        if (fornecedorDTO.getCnpj().length() != 14) {
            long valor = fornecedorDTO.getCnpj().length();
            throw new IllegalArgumentException(String.format("O número de caracteres permitidos é 14! Você está colocando %s", valor));
        }

        if (!(StringUtils.isNumeric(fornecedorDTO.getTelefone()))){
            throw new IllegalArgumentException("Telefone não deve conter letras.");
        }

        if (fornecedorDTO.getTelefone().length() < 13 || fornecedorDTO.getTelefone().length() > 14) {
            long valorT = fornecedorDTO.getTelefone().length();
            throw new IllegalArgumentException(String.format("São permitidos de 13 até 14 caracteres! Você está colocando %s", valorT));
        }
    }

    public Fornecedor findByFornecedorCnpj(String cnpj) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findByCnpj(cnpj);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", cnpj));
    }

    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            Fornecedor fornecedor = fornecedorOptional.get();
            return FornecedorDTO.of(fornecedor);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findByFornecedorId(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s don't exist", id));
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        this.validate(fornecedorDTO);
        Optional<Fornecedor> fornecedorExisteOptional = this.fornecedorRepository.findById(id);

        if (fornecedorExisteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExisteOptional.get();
            Fornecedor fornecedor;
            fornecedor = findByFornecedorId(id);
            List<Categoria> categorias = categoriaService.findByFornecedor(fornecedor);

            LOGGER.info("Atualizando br.com.hbsis.fornecedor... id: [{}]", fornecedorDTO.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Usuario Existente: {}", fornecedorExistente);

            fornecedorExistente.setRazao(fornecedorExistente.getRazao());
            fornecedorExistente.setNome(fornecedorExistente.getNome());
            fornecedorExistente.setEndereco(fornecedorExistente.getEndereco());
            fornecedorExistente.setTelefone(fornecedorExistente.getTelefone());
            fornecedorExistente.setEmail(fornecedorExistente.getEmail());
            fornecedorExistente.setCnpj(fornecedorExistente.getCnpj());

            fornecedorExistente = this.fornecedorRepository.save(fornecedorExistente);

            for (Categoria categoria : categorias) {
                categoria.setCodigoCategoria(categoria.getCodigoCategoria().substring(7, 10));
                categoria.setFornecedor(fornecedorExistente);
                categoriaService.update(CategoriaDTO.of(categoria), categoria.getIdCategoria());
            }

            return FornecedorDTO.of(fornecedorExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para br.com.hbsis.fornecedor de ID: [{}]", id);

        this.fornecedorRepository.deleteById(id);
    }


}
