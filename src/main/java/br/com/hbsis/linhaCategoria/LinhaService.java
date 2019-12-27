package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.validacoes.StringValidations;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaService.class);
    private final ILinhaRepository iLinhaRepository;
    private final CategoriaService categoriaService;
    private final StringValidations stringValidations;

    public LinhaService(ILinhaRepository iLinhaRepository, CategoriaService categoriaService, StringValidations stringValidations) {
        this.iLinhaRepository = iLinhaRepository;
        this.categoriaService = categoriaService;
        this.stringValidations = stringValidations;
    }

    public LinhaDTO save(LinhaDTO linhaDTO) {
        this.validate(linhaDTO);

        LOGGER.info("Salvando linha");
        LOGGER.debug("Linha: {}", linhaDTO);

        Linha linha = new Linha();
        linha.setNomeLinha(linhaDTO.getNomeLinha());
        linha.setCategoriaLinha(categoriaService.existsByCategoriaLinha(linhaDTO.getCategoriaLinha()));
        linha.setCodigoLinha(stringValidations.zeroAEsquerda(linhaDTO.getCodigoLinha().toUpperCase()));

        linha = this.iLinhaRepository.save(linha);

        return linhaDTO.of(linha);
    }

    private void validate(LinhaDTO linhaDTO) {
        LOGGER.info("Validando linha");

        if (linhaDTO == null) {
            throw new IllegalArgumentException("Linha não deve ser nula");
        }

    }

    public Linha findById(long id) {
        Optional<Linha> linhaOptional = this.iLinhaRepository.findById(id);
        System.out.println(id);

        if (linhaOptional.isPresent()) {
            return linhaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID não existe", id));
    }

    public Linha findByLinhaCodigoLinha(String codigoLinha) {
        Optional<Linha> linhaSecundaria = this.iLinhaRepository.findByCodigoLinha(codigoLinha);

        if (linhaSecundaria.isPresent()) {
            return linhaSecundaria.get();
    }

        throw new IllegalArgumentException(String.format("codigo Linha  %s não existe", codigoLinha));
    }

    public LinhaDTO findLinha(Long codigoLinha) {
        Optional<Linha> linhaSecundaria = this.iLinhaRepository.findById(codigoLinha);

        if (linhaSecundaria.isPresent()) {
            return LinhaDTO.of(linhaSecundaria.get());
        }

        // '%s' serve como uma marcação para quando der 'String.format', que aloca a variavel que contém o conteúdo.
        throw new IllegalArgumentException(String.format("ID %s não existe", codigoLinha));
    }

    public LinhaDTO update(LinhaDTO linhaDTO, Long codigoLinha) {
        this.validate(linhaDTO);
        Optional<Linha> linhaSecundariaExiste = this.iLinhaRepository.findById(codigoLinha);

        if (linhaSecundariaExiste.isPresent()) {
            Linha linhaExistente = linhaSecundariaExiste.get();

            LOGGER.info("Atualizando br.com.hbsis.fornecedor... id: [{}]", linhaDTO.getCodigoLinha());
            LOGGER.debug("Payload: {}", linhaDTO);
            LOGGER.debug("Usuario Existente: {}", linhaSecundariaExiste);

            linhaExistente.setNomeLinha(linhaDTO.getNomeLinha());
            linhaExistente.setCategoriaLinha(categoriaService.existsByCategoriaLinha(linhaDTO.getCategoriaLinha()));

            linhaExistente = this.iLinhaRepository.save(linhaExistente);

            return linhaDTO.of(linhaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigoLinha));
    }

    public void delete(Long codigoLinha){
        LOGGER.info("Executand odelete na linha: [{}]", codigoLinha);

        this.iLinhaRepository.deleteById(codigoLinha);
    }
}
