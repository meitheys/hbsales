package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.CategoriaService;
import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaService.class);
    private ILinhaRepository iLinhaRepository;
    private CategoriaService categoriaService;

    public LinhaService(ILinhaRepository iLinhaRepository, CategoriaService categoriaService) {
        this.iLinhaRepository = iLinhaRepository;
        this.categoriaService = categoriaService;
    }

    public LinhaDTO save(LinhaDTO linhaDTO) {
        this.validate(linhaDTO);

        LOGGER.info("Salvando linha");
        LOGGER.debug("Linha: {}", linhaDTO);

        Linha linha = new Linha();
        linha.setCodigo_linha(linhaDTO.getCodigo_linha());
        linha.setCategoria_linha(linhaDTO.getCategoria_linha());
        linha.setNome_linha(linhaDTO.getNome_linha());
        linha = this.iLinhaRepository.save(linha);

        return linhaDTO.of(linha);
    }

    private void validate(LinhaDTO linhaDTO) {
        LOGGER.info("Validando linha");

        if (linhaDTO == null) {
            throw new IllegalArgumentException("Linha não deve ser nula");
        }

    }

    public LinhaDTO findLinha(Long codigo_linha) {
        Optional<Linha> linhaSecundaria = this.iLinhaRepository.findById(codigo_linha);

        if (linhaSecundaria.isPresent()) {
            return LinhaDTO.of(linhaSecundaria.get());
        }

        // '%s' serve como uma marcação para quando der 'String.format', que aloca a variavel que contém o conteúdo.
        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_linha));
    }

    public LinhaDTO update(LinhaDTO linhaDTO, Long codigo_linha) {
        Optional<Linha> linhaSecundariaExiste = this.iLinhaRepository.findById(codigo_linha);

        if (linhaSecundariaExiste.isPresent()) {
            Linha linhaExistente = linhaSecundariaExiste.get();

            LOGGER.info("Atualizando br.com.hbsis.fornecedor... id: [{}]", linhaDTO.getCodigo_linha());
            LOGGER.debug("Payload: {}", linhaDTO);
            LOGGER.debug("Usuario Existente: {}", linhaSecundariaExiste);

            linhaExistente.setNome_linha(linhaDTO.getNome_linha());
            linhaExistente.setCategoria_linha(linhaDTO.getCategoria_linha());
            linhaExistente = this.iLinhaRepository.save(linhaExistente);

            return linhaDTO.of(linhaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_linha));
    }

    public void delete(Long codigo_linha){
        LOGGER.info("Executand odelete na linha: [{}]", codigo_linha);

        this.iLinhaRepository.deleteById(codigo_linha);
    }

    //Excel ---------------

    public void findAll(HttpServletResponse resposta) throws Exception {
        String arquivo = "linhas.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter escritor = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(escritor).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"codigo_linha", "categoria_linha", "nome_linha"};
        icsvWriter.writeNext(tituloCSV);

        for (Linha linhas : iLinhaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{linhas.getCodigo_linha().toString(), linhas.getCategoria_linha().toString(), linhas.getNome_linha().toString()});
        }

    }

    public List<Linha> leitorTotal(MultipartFile importacao) throws Exception {
        InputStreamReader insercao = new InputStreamReader(importacao.getInputStream());

        //Perguntar
        CSVReader leitor = new CSVReaderBuilder(insercao).withSkipLines(1).build();

        List<String[]> linhaS = leitor.readAll();
        List<Linha> resultado = new ArrayList<>();

        for (String[] linha : linhaS) {

            try {

                //Quebrando o arquivo CSV em valores.

                String[] dados = linha[0].replaceAll("\"", "").split(";");

                Linha linhaclasse = new Linha();

                //dados[x] = dado pego baseado na formatação csv

                linhaclasse.setCodigo_linha(Long.parseLong(dados[0]));
                linhaclasse.setCategoria_linha(Long.parseLong(dados[1]));
                linhaclasse.setNome_linha((dados[2]));

                resultado.add(linhaclasse);
                System.out.println(resultado);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return iLinhaRepository.saveAll(resultado);

    }


}
