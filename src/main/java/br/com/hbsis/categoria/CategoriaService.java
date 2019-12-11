package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;

import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
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

        Fornecedor fornecedorDTO = fornecedorService.findIdFornecedor(categoriaDTO.getFornecedor());

        LOGGER.info("Salvando categoria...");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();

        String codigo = categoriaDTO.getCodigo_categoria();
        String cnpjota = fornecedorDTO.getCnpj();

        String codigoProcessed = codigoValidar(codigo);
        String cnpjProcessed = quatroCNPJ(cnpjota);

        String fim = "CAT" + cnpjProcessed + codigoProcessed;

        categoria.setCodigoCategoria(fim);
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(fornecedorService.findIdFornecedor(categoriaDTO.getFornecedor()));

        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);

    }

    public String codigoValidar (String codigo){
        String codigoProcessador = StringUtils.leftPad(codigo, 3, "0");

        return codigoProcessador;

    }


    public String desformatando(String cnpj) {

        String desformatando =   cnpj.charAt(0)+""+cnpj.charAt(1)+
                cnpj.charAt(3)+cnpj.charAt(4)+cnpj.charAt(5)+
                cnpj.charAt(7)+cnpj.charAt(8)+cnpj.charAt(9)+
                cnpj.charAt(11)+cnpj.charAt(12)+cnpj.charAt(13)+cnpj.charAt(14)+
                cnpj.charAt(16)+cnpj.charAt(17);

        return desformatando;

    }

    //4 ULTIMOS NUMEROS
    public String quatroCNPJ(String cnpj){
        String ultimosDigitos = cnpj.substring(cnpj.length() - 4);

        return ultimosDigitos;
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
        System.out.println(categoriaDTO.getCodigo_categoria());
        System.out.println(categoriaDTO.getNomeCategoria());
        if (StringUtils.isEmpty(categoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Categoria não deve ser vazio!!");
        }

    }

    public Categoria findByIdString(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID não existe", id));
    }

    public  Categoria existsByCategoriaLinha(String categoriaLinha) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findByCodigoCategoria(categoriaLinha);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("categoria_linha não existe", categoriaLinha));
    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando categoria... id: [{}]", categoriaExistente.getCodigoCategoria());
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

    public String formatarCnpj(String cnpj) {
        Fornecedor fornecedor = new Fornecedor();
        String mask = fornecedor.getCnpj();
        mask = (cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14));
        return mask;
    }


    //Trabalhando com Excel ---------------------------------------------------------------------------

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
                    formatarCnpj(linhas.getFornecedor().getCnpj())
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






}
