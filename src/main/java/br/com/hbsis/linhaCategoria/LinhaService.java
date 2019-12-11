package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import com.opencsv.*;
import freemarker.template.utility.StringUtil;
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
    private final ILinhaRepository iLinhaRepository;
    private final CategoriaService categoriaService;
    private final ICategoriaRepository iCategoriaRepository;


    public LinhaService(ILinhaRepository iLinhaRepository, CategoriaService categoriaService, ICategoriaRepository iCategoriaRepository) {
        this.iLinhaRepository = iLinhaRepository;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = iCategoriaRepository;
    }

    public LinhaDTO save(LinhaDTO linhaDTO) {
        this.validate(linhaDTO);

        LOGGER.info("Salvando linha");
        LOGGER.debug("Linha: {}", linhaDTO);

        Linha linha = new Linha();
        linha.setNomeLinha(linhaDTO.getNomeLinha());
        linha.setCategoriaLinha(categoriaService.existsByCategoriaLinha(linhaDTO.getCategoriaLinha()));
        linha.setCodigoLinha(zeroAEsquerda(linhaDTO.getCodigoLinha().toUpperCase()));

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

    public LinhaDTO findLinha(Long codigo_linha) {
        Optional<Linha> linhaSecundaria = this.iLinhaRepository.findById(codigo_linha);

        if (linhaSecundaria.isPresent()) {
            return LinhaDTO.of(linhaSecundaria.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_linha));
    }

    public LinhaDTO update(LinhaDTO linhaDTO, Long codigo_linha) {
        Optional<Linha> linhaSecundariaExiste = this.iLinhaRepository.findById(codigo_linha);

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

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_linha));
    }

    public void delete(Long codigo_linha){
        LOGGER.info("Executand odelete na linha: [{}]", codigo_linha);

        this.iLinhaRepository.deleteById(codigo_linha);
    }

    public String zeroAEsquerda(String zero){
        String zerinho = StringUtil.leftPad(zero, 10, "0");

        return zerinho;
    }

    //Excel ---------------



    public void findAll(HttpServletResponse resposta) throws Exception {
        String arquivo = "linha.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter escritor = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(escritor).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"nome_linha", "codigo_linha", "codigo_categoria", "nome_categoria" };
        icsvWriter.writeNext(tituloCSV);

        for (Linha linhas : iLinhaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{linhas.getNomeLinha(), linhas.getCodigoLinha(), String.valueOf(linhas.getCategoriaLinha().getCodigoCategoria()), linhas.getCategoriaLinha().getNomeCategoria()});
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
                Categoria cat = new Categoria();


                linhaclasse.setNomeLinha((dados[0]));
                linhaclasse.setCodigoLinha(dados[1]);
                cat.setCodigoCategoria(dados[2]);
                cat.setNomeCategoria(dados[3]);

                resultado.add(linhaclasse);
                System.out.println(resultado);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return iLinhaRepository.saveAll(resultado);

    }


}
