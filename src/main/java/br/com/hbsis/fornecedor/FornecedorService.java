package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
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

    }


    public FornecedorDTO findById(Long id) {
            Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

            if (fornecedorOptional.isPresent()) {
                Fornecedor fornecedor = fornecedorOptional.get();
                return FornecedorDTO.of(fornecedor);
            }

            throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findIdFornecedor (Long id){
        Optional<Fornecedor> fornecedorOptional1 = this.fornecedorRepository.findById(id);

        if (fornecedorOptional1.isPresent()) {
            return fornecedorOptional1.get();
        }

        throw new IllegalArgumentException(String.format("Fornecedor não existe: %s", id));
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExisteOptional = this.fornecedorRepository.findById(id);

        if (fornecedorExisteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExisteOptional.get();

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

            return FornecedorDTO.of(fornecedorExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para br.com.hbsis.fornecedor de ID: [{}]", id);

        this.fornecedorRepository.deleteById(id);
    }


}
