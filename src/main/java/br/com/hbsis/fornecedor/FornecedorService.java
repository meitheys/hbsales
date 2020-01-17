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

        //Validação cnpj, se tem 14 caracteres e se são números.

        boolean valida = false;
        String forne = String.valueOf(fornecedorDTO.getCnpj());
        String telefoneN = fornecedorDTO.getTelefone();
        for (int i = 0; i < forne.length(); ++i) {
            char ch = forne.charAt(i);
            if (!(ch >= '0' && ch <= '9')) {
                valida = true;
            }

        }

        if (valida == true) {
            throw new IllegalArgumentException("Caracteres não permitidos.");
        }

        if (forne.length() != 14) {
            long valor = forne.length();
            throw new IllegalArgumentException(String.format("O número de caracteres permitidos é 14! Você está colocando %s", valor));
        }

        //Validação Telefone, se tem 13 caracteres e se são números.

        boolean validaCel = false;
        for (int i = 0; i < telefoneN.length(); ++i) {
            char ch = telefoneN.charAt(i);
            if (!(ch >= '0' && ch <= '9')) {
                validaCel = true;
            }
        }

        if (validaCel == true) {
            throw new IllegalArgumentException("Caracteres não permitidos.");
        }

        if (telefoneN.length() < 13 || telefoneN.length() > 14) {
            long valorT = telefoneN.length();
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

    public boolean existsById(Long id) {
        return fornecedorRepository.existsById(id);
    }

    public boolean existsByCnpj(String cnpj) {
        return fornecedorRepository.existsByCnpj(cnpj);
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
