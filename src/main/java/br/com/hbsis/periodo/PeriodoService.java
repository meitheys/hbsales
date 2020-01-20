package br.com.hbsis.periodo;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PeriodoService {

    public static Logger LOGGER = LoggerFactory.getLogger(PeriodoService.class);

    public IPeriodoRepository iPeriodoRepository;
    private FornecedorService fornecedorService;

    public PeriodoService(IPeriodoRepository iPeriodoRepository, FornecedorService fornecedorService) {
        this.iPeriodoRepository = iPeriodoRepository;
        this.fornecedorService = fornecedorService;
    }

    public PeriodoDTO save(PeriodoDTO periodoDTO) {
        this.validate(periodoDTO);
        LOGGER.info("Salvando periodo...");

        Periodo periodo = new Periodo();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor = fornecedorService.findByFornecedorId(periodoDTO.getIdFornecedor());
        periodo.setIdFornecedor(fornecedor);
        periodo.setDataFinal(periodoDTO.getDataFinal());
        periodo.setDataInicial(periodoDTO.getDataFinal());
        periodo.setRetirada(periodoDTO.getRetirada());

        periodo = this.iPeriodoRepository.save(periodo);

        return periodoDTO.of(periodo);
    }

    public PeriodoDTO findById(Long id) {
        Optional<Periodo> optional = this.iPeriodoRepository.findById(id);

        if (optional.isPresent()) {
            return PeriodoDTO.of(optional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Periodo findByPeriodoId(Long id) {
        Optional<Periodo> optional = this.iPeriodoRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void validate(PeriodoDTO periodoDTO) {
        LOGGER.info("Validando periodo");
        if (periodoDTO == null) {
            throw new IllegalArgumentException("Periodo está nulo!");
        }
        if (periodoDTO.getDataInicial().toString().isEmpty()) {
            throw new IllegalArgumentException("Data inicial não foi informada");
        }
        if (periodoDTO.getDataFinal().toString().isEmpty()) {
            throw new IllegalArgumentException("Data final não foi informada");
        }
        if (periodoDTO.getDataInicial().isBefore(LocalDate.now()) || periodoDTO.getDataFinal().isBefore(LocalDate.now()) || periodoDTO.getRetirada().isBefore(LocalDate.now())){
            throw new  IllegalArgumentException("A data está invalida, apontando que data um dia anterior a hoje!");
        }
    }

    public PeriodoDTO update(PeriodoDTO periodoDTO, Long id) {
        this.validate(periodoDTO);
        Optional<Periodo> periodoDuplicado = this.iPeriodoRepository.findById(id);

        if (periodoDuplicado.isPresent()) {
            Periodo periodo = periodoDuplicado.get();

            LOGGER.info("Atualizando periodo... id: [{}]", periodo.getId());
            LOGGER.debug("Payload: {}", periodoDTO);

            periodo.setRetirada(periodoDTO.getRetirada());
            periodo.setIdFornecedor(fornecedorService.findByFornecedorId(periodoDTO.getIdFornecedor()));
            periodo.setDataInicial(periodoDTO.getDataInicial());
            periodo.setDataFinal(periodoDTO.getDataFinal());

            periodo = this.iPeriodoRepository.save(periodo);

            return periodoDTO.of(periodo);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando periodo de ID: [{}]", id);

        this.iPeriodoRepository.deleteById(id);
    }

}
