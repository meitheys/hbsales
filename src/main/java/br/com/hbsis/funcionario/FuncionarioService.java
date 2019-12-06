package br.com.hbsis.funcionario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);
    private IFuncionarioRepository iFuncionarioRepository;

    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository){
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        this.validate(funcionarioDTO);

        LOGGER.info("Salvando funcionario");
        LOGGER.debug("Funcionario {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioDTO.getId());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setNomeFuncionario(funcionarioDTO.getEmail());
        funcionario = this.iFuncionarioRepository.save(funcionario);

        return funcionarioDTO.of(funcionario);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando funcionario");

        if(funcionarioDTO == null)
            throw new IllegalArgumentException("Funcionario nulo!");
    }

    public FuncionarioDTO findFuncionario(Long id){
        Optional<Funcionario> funcionarioSecundario = this.iFuncionarioRepository.findById(id);

        if(funcionarioSecundario.isPresent()) {
            return FuncionarioDTO.of(funcionarioSecundario.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, Long id){
        Optional<Funcionario> funcionarioSecundarioExiste = this.iFuncionarioRepository.findById(id);

        if(funcionarioSecundarioExiste.isPresent()) {
            Funcionario funcionarioExiste = funcionarioSecundarioExiste.get();

            LOGGER.info("Atualizando funcionario, id: [{}]", funcionarioDTO.getId());
            LOGGER.debug("Payload: {}", funcionarioDTO);

            funcionarioExiste.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
            funcionarioExiste.setEmail(funcionarioDTO.getEmail());
            funcionarioExiste = this.iFuncionarioRepository.save(funcionarioExiste);

            return funcionarioDTO.of(funcionarioExiste);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public void delete(Long id) {
        LOGGER.info("Delete em funcionario: ", id);

        this.iFuncionarioRepository.deleteById(id);
    }


}
