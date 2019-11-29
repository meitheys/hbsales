package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import com.microsoft.sqlserver.jdbc.StringUtils;
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
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private ICategoriaRepository iCategoriaRepository;
    private final FornecedorService fornecedorService;

    public CategoriaService(ICategoriaRepository iCategoriaRepository, FornecedorService fornecedorService) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        this.validate(categoriaDTO);

        LOGGER.info("Salvando categoria...");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(fornecedorService.findIdFornecedor(categoriaDTO.getFornecedor()));
        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);

    }

    private void validate(CategoriaDTO categoriaDTO) {
        LOGGER.info("Validando Categoria");

        if (categoriaDTO == null) {
            throw new IllegalArgumentException("!!Categoria Nula!!");
        }
        if (categoriaDTO.getFornecedor() == null) {
            System.out.println("!!Fornecedor está vazio!!");
        } else {
            System.out.println("Fornecedor: " + categoriaDTO.getFornecedor());
        }
        System.out.println(categoriaDTO.getNomeCategoria());
        if (StringUtils.isEmpty(categoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Categoria não deve ser vazio!!");
        }
    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID não existe", id));
    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando categoria... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Categoria já existe: {}", categoriaExistente);

            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());
            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return CategoriaDTO.of(categoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para categoria, id: [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }

    //Trabalhando com Excel ---------------------------------------------------------------------------

    public void findAll(HttpServletResponse resposta) throws Exception {
        String arquivo = "categoria.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter escritor = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(escritor).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"id_categoria", "nome_categoria", "id_fornecedor"};
        icsvWriter.writeNext(tituloCSV);

        for (Categoria linhas : iCategoriaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{linhas.getId().toString(), linhas.getNomeCategoria(), linhas.getFornecedor().toString()});
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
                Fornecedor fornecedor = fornecedorService.findIdFornecedor(Long.parseLong(dados[1]));
                categoria.setFornecedor(fornecedor);

                resultado.add(categoria);
                System.out.println(resultado);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return iCategoriaRepository.saveAll(resultado);

    }

    public List<Categoria> readAll(MultipartFile file) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();
        List<String[]> linhas = csvReader.readAll();
        List<Categoria> resultado = new ArrayList<>();

        for (String[] linha : linhas) {
            try {
                String[] dados = linha[1].replaceAll("\"", "").split(";");

                Categoria categoria = new Categoria();
                Fornecedor fornecedor = new Fornecedor();
                FornecedorDTO fornecedorDTO = new FornecedorDTO();


                categoria.setId(Long.parseLong(dados[0]));
                categoria.setNomeCategoria((dados[2]));
                fornecedorDTO = fornecedorService.findById(Long.parseLong(dados[1]));

                fornecedor.setIdFornecedor(fornecedorDTO.getId());
                fornecedor.setCnpj(fornecedorDTO.getCnpj());
                fornecedor.setRazao(fornecedorDTO.getRazao());
                fornecedor.setNome(fornecedorDTO.getNome());
                fornecedor.setEndereco(fornecedorDTO.getEndereco());
                fornecedor.setTelefone(fornecedorDTO.getTelefone());
                fornecedor.setEmail(fornecedorDTO.getEmail());

                categoria.setFornecedor(fornecedor);

                resultado.add(categoria);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iCategoriaRepository.saveAll(resultado);
    }


}