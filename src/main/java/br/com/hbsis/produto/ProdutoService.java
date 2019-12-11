package br.com.hbsis.produto;

import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.fornecedor.FornecedorRepository;
import br.com.hbsis.linhaCategoria.LinhaService;
import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private final IProdutoRepository iProdutoRepository;
    private final LinhaService linhaService;
    private final CategoriaService categoriaService;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(IProdutoRepository iProdutoRepository, LinhaService linhaService, CategoriaService categoriaService, FornecedorRepository fornecedorRepository) {
        this.iProdutoRepository = iProdutoRepository;
        this.categoriaService = categoriaService;
        this.linhaService = linhaService;
        this.fornecedorRepository = fornecedorRepository;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();
        produto.setUnidadePeso(produtoDTO.getUnidadePeso());
        produto.setLinha(linhaService.findByCodigoLinha(produtoDTO.getCodigoProduto()));
        produto.setCodigoProduto(produtoDTO.getCodigoProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPeso(produtoDTO.getPeso());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidade(produtoDTO.getUnidades());
        produto.setValidade(produtoDTO.getValidade());

        produto = this.iProdutoRepository.save(produto);

        return produtoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando produto");

        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo");
        }

    }

    public ProdutoDTO findById(Long codigo_produto) {
        Optional<Produto> produtoDuplicado = this.iProdutoRepository.findById(codigo_produto);

        if (produtoDuplicado.isPresent()) {
            Produto produto = produtoDuplicado.get();
            return ProdutoDTO.of(produto);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_produto));
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long codigo_produto) {
        Optional<Produto> produtoDuplicadoDois = this.iProdutoRepository.findById(codigo_produto);

        if (produtoDuplicadoDois.isPresent()) {
            Produto produtoDuplicado = produtoDuplicadoDois.get();

            LOGGER.info("Atualizando Produto... id: [{}]", produtoDTO.getCodigoProduto());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto já existe: {}", produtoDuplicado);

            produtoDuplicado.setValidade(produtoDuplicado.getValidade());
            produtoDuplicado.setUnidade(produtoDuplicado.getUnidade());
            produtoDuplicado.setPrecoProduto(produtoDuplicado.getPrecoProduto());
            produtoDuplicado.setPeso(produtoDuplicado.getPeso());
            produtoDuplicado.setNomeProduto(produtoDuplicado.getNomeProduto());
            produtoDuplicado.setCodigoProduto(produtoDuplicado.getCodigoProduto());
            produtoDuplicado.setLinha(produtoDuplicado.getLinha());

            produtoDuplicado = this.iProdutoRepository.save(produtoDuplicado);

            return ProdutoDTO.of(produtoDuplicado);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", codigo_produto));
    }

    public void delete(Long codigo_produto) {
        LOGGER.info("Executando delete para produto de ID: [{}]", codigo_produto);

        this.iProdutoRepository.deleteById(codigo_produto);
    }

 /*   public List<Produto> produto(MultipartFile importacao) throws Exception {
        InputStreamReader insercao = new InputStreamReader(importacao.getInputStream());

        //Perguntar
        CSVReader leitor = new CSVReaderBuilder(insercao).withSkipLines(1).build();

        List<String[]> linhaS = leitor.readAll();
        List<Produto> resultado = new ArrayList<>();

        for (String[] linha : linhaS) {

            try {

                //Quebrando o arquivo CSV em valores.

                String[] dados = linha[0].replaceAll("\"", "").split(";");

                Produto produto = new Produto();

                //dados[x] = dado pego baseado na formatação csv

                produto.setValidade((LocalDate.parse(dados[6])));
                produto.setUnidade(Long.parseLong(dados[4]));
                produto.setPrecoProduto((Double.parseDouble(dados[2])));
                produto.setPeso((Double.parseDouble(dados[5])));
                produto.setNomeProduto((dados[1]));
                produto.setLinha(categoriaService.existsByCategoriaLinha(linhaDTO.getCategoriaLinha()));
                produto.setCodigoProduto((dados[0]));

                resultado.add(produto);
                System.out.println(produto);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return iProdutoRepository.saveAll(resultado);

    }*/

    //Excel

    public void findAll(HttpServletResponse resposta) throws Exception {
        String arquivo = "produtos.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter escritor = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(escritor).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"codigo_produto", "nome_produto", "unidades_por_caixa", "peso_unidade", "codigo_linha", "nome_linha", "codigo_categoria", "nome_categoria", "cnpj_fornecedor", "razão_fornecedor"};
        icsvWriter.writeNext(tituloCSV);

        for (Produto linhas : iProdutoRepository.findAll()) {
            icsvWriter.writeNext(new String[]{linhas.getCodigoProduto(), linhas.getNomeProduto(), String.valueOf(linhas.getUnidade()), String.valueOf(linhas.getPeso()), linhas.getLinha().getCodigoLinha() });
        }
    }

    //Atividade 11 ______________________________________________//________________________________________________

    public void acharFornecedorProduto(Long idFornecedor, MultipartFile multipartFile) throws Exception {

        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());

        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

        List<String[]> totalLido = csvReader.readAll();

        for (String[] linhas : totalLido) {
            try {
                String[] dado = linhas[0].replaceAll("\"c", "").split(";");

                Produto produto = new Produto();
                if (fornecedorRepository.existsById(idFornecedor)) {
                    produto.setValidade((LocalDate.parse(dado[6])));
                    produto.setUnidade(Long.parseLong(dado[4]));
                    produto.setPrecoProduto((Double.parseDouble(dado[2])));
                    produto.setPeso((Double.parseDouble(dado[5])));
                    produto.setNomeProduto((dado[1]));
                    produto.setCodigoProduto(dado[0]);

                    if (iProdutoRepository.existsById(produto.getIdProduto())) {
                        produto.setIdProduto(iProdutoRepository.findById(produto.getIdProduto()).get().getIdProduto());
                        update(ProdutoDTO.of(produto), produto.getIdProduto());
                    } else {
                        LOGGER.info("Produto {} pertence a outro fornecedor", produto.getIdProduto());
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*public List<Produto> leitorTotal(MultipartFile importacao) throws Exception {
        InputStreamReader insercao = new InputStreamReader(importacao.getInputStream());

        //Perguntar
        CSVReader leitor = new CSVReaderBuilder(insercao).withSkipLines(1).build();

        List<String[]> linhaS = leitor.readAll();
        List<Produto> resultado = new ArrayList<>();

        for (String[] linha : linhaS) {

            try {

                //Quebrando o arquivo CSV em valores.

                String[] dados = linha[0].replaceAll("\"", "").split(";");

                Produto produto = new Produto();

                //dados[x] = dado pego baseado na formatação csv

                produto.setValidade((LocalDate.parse(dados[6])));
                produto.setUnidade(Long.parseLong(dados[4]));
                produto.setPrecoProduto((Double.parseDouble(dados[2])));
                produto.setPeso((Double.parseDouble(dados[5])));
                produto.setNomeProduto((dados[1]));
                produto.setLinha((Long.parseLong(dados[3])));
                produto.setCodigoProduto(((dados[0])));

                resultado.add(produto);
                System.out.println(produto);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return iProdutoRepository.saveAll(resultado);

    }*/
}


