package br.com.hbsis.periodo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeriodoService {

    public static Logger LOGGER = LoggerFactory.getLogger(PeriodoService.class);

    public IPeriodoRepository iPeriodoRepository;

    public PeriodoService(IPeriodoRepository iPeriodoRepository) {
        this.iPeriodoRepository = iPeriodoRepository;
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

    public PeriodoDTO save(PeriodoDTO periodoDTO) {
        this.validate(periodoDTO);
        LOGGER.info("Salvando periodo...");

        Periodo periodo = new Periodo();

        periodo.setData_final(periodoDTO.getData_final());
        periodo.setData_inicial(periodoDTO.getData_final());
        periodo.setId(periodoDTO.getId());
        periodo.setIdFornecedor(periodoDTO.getIdFornecedor());
        periodo.setRetirada(periodoDTO.getRetirada());

        periodo = this.iPeriodoRepository.save(periodo);

        return periodoDTO.of(periodo);
    }

    public void validate(PeriodoDTO periodoDTO) {
        LOGGER.info("Validando periodo");
        if (periodoDTO == null) {
            throw new IllegalArgumentException("Periodo está nulo!");
        }
        if (periodoDTO.getData_inicial().toString().isEmpty()) {
            throw new IllegalArgumentException("Data inicial não foi informada");
        }
        if (periodoDTO.getData_final().toString().isEmpty()) {
            throw new IllegalArgumentException("Data final não foi informada");
        }
    }

    public PeriodoDTO update(PeriodoDTO periodoDTO, Long id) {
        Optional<Periodo> periodoDuplicado = this.iPeriodoRepository.findById(id);

        if (periodoDuplicado.isPresent()) {
            Periodo periodo = periodoDuplicado.get();

            LOGGER.info("Atualizando periodo... id: [{}]", periodo.getId());
            LOGGER.debug("Payload: {}", periodoDTO);

            periodo.setRetirada(periodoDTO.getRetirada());
            periodo.setIdFornecedor(periodoDTO.getIdFornecedor());
            periodo.setData_inicial(periodoDTO.getData_inicial());
            periodo.setData_final(periodoDTO.getData_final());

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
