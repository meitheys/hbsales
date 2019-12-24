package br.com.hbsis.csv;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.validacoes.StringValidations;
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
public class CategoriaCSV {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaCSV.class);
    private ICategoriaRepository iCategoriaRepository;
    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;
    private final StringValidations stringValidations;

    public CategoriaCSV(ICategoriaRepository iCategoriaRepository, FornecedorService fornecedorService, CategoriaService categoriaService, StringValidations stringValidations) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
        this.stringValidations = stringValidations;
    }

    public void findAll(HttpServletResponse resposta) throws Exception {
        String arquivo = "categoria.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter escritor = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(escritor).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"codigo_categoria","nome_categoria", "razao", "cnpj"};
        icsvWriter.writeNext(tituloCSV);

        for (Categoria linhas : iCategoriaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{
                    linhas.getCodigoCategoria(),
                    linhas.getNomeCategoria(),
                    linhas.getFornecedor().getRazao(),
                    stringValidations.formatarCnpj(linhas.getFornecedor().getCnpj())
            });
        }
    }

    public List<Categoria> leitorTotal(MultipartFile importacao) throws Exception {
        InputStreamReader insercao = new InputStreamReader(importacao.getInputStream());

        //Perguntar
        CSVReader leitor = new CSVReaderBuilder(insercao).withSkipLines(1).build();

        List<String[]> linhaS = leitor.readAll();
        List<Categoria> resultado = new ArrayList<>();

        for (String[] linha : linhaS) {

            try {

                //Quebrando o arquivo CSV em valores.

                String[] dados = linha[0].replaceAll("\"", "").split(";");

                Categoria categoria = new Categoria();

                //dados[x] = dado pego baseado na formatação csv

                categoria.setCodigoCategoria(dados[0]);
                categoria.setNomeCategoria((dados[2]));
                Fornecedor fornecedor = fornecedorService.findByFornecedorId(Long.parseLong(dados[1]));
                categoria.setFornecedor(fornecedor);

                resultado.add(categoria);
                System.out.println(resultado);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return iCategoriaRepository.saveAll(resultado);

    }
}
