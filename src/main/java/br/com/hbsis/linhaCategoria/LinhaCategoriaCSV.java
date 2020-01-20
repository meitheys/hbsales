package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.Categoria;
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

@Service
public class LinhaCategoriaCSV {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaCSV.class);
    private final ILinhaRepository iLinhaRepository;

    public LinhaCategoriaCSV(ILinhaRepository iLinhaRepository) {
        this.iLinhaRepository = iLinhaRepository;
    }

    public void exportarCSV(HttpServletResponse resposta) throws Exception {
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

    public List<Linha> importarCSV(MultipartFile importacao) throws Exception {
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
