package br.com.hbsis.funcionario;

import br.com.hbsis.HbApi.HbApiService;
import br.com.hbsis.HbApi.employee.EmployeeSavingDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);
    private IFuncionarioRepository iFuncionarioRepository;
    private final HbApiService hbApiService;

    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository, HbApiService hbApiService) {
        this.iFuncionarioRepository = iFuncionarioRepository;
        this.hbApiService = hbApiService;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) throws IOException {
        this.validate(funcionarioDTO);

        LOGGER.info("Salvando funcionario");
        LOGGER.debug("Funcionario {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuid(funcionarioDTO.getUuid());
        funcionario.setNomeFuncionario(funcionarioDTO.getNome());
        funcionario = this.iFuncionarioRepository.save(funcionario);

        return funcionarioDTO.of(funcionario);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando funcionario");

        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("Funcionario nulo!");
        }

        hbEmplyeeAdminFuncionario(funcionarioDTO);
        if (StringUtils.isEmpty(funcionarioDTO.getUuid())){
            throw new IllegalArgumentException("UUID do funcionário não deve ser nulo");
        }

    }

    public Funcionario findByFuncionarioId(Long id) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            return funcionarioOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    private void hbEmplyeeAdminFuncionario(FuncionarioDTO funcionarioDTO) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f59fec50-1b67-11ea-978f-2e728ce88125");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(funcionarioDTO, headers);
        ResponseEntity<EmployeeSavingDTO> response = template.exchange("http://10.2.54.25:9999/api/employees", HttpMethod.POST, httpEntity, EmployeeSavingDTO.class);
        funcionarioDTO.setUuid(Objects.requireNonNull(response.getBody()).getEmployeeUuid());
        funcionarioDTO.setNome(response.getBody().getEmployeeName());
    }

    public FuncionarioDTO findById(Long id) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();
            return FuncionarioDTO.of(funcionario);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FuncionarioDTO findFuncionario(Long id) {
        Optional<Funcionario> funcionarioSecundario = this.iFuncionarioRepository.findById(id);

        if (funcionarioSecundario.isPresent()) {
            return FuncionarioDTO.of(funcionarioSecundario.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, Long id) {
        this.validate(funcionarioDTO);
        Optional<Funcionario> funcionarioSecundarioExiste = this.iFuncionarioRepository.findById(id);

        if (funcionarioSecundarioExiste.isPresent()) {
            Funcionario funcionarioExiste = funcionarioSecundarioExiste.get();

            LOGGER.info("Atualizando funcionario, id: [{}]", funcionarioDTO.getId());
            LOGGER.debug("Payload: {}", funcionarioDTO);

            funcionarioExiste.setNomeFuncionario(funcionarioDTO.getNome());
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
