package br.com.hbsis.produto;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaCategoria.Linha;
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
public class ProdutoCSV {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoCSV.class);
    private final IProdutoRepository iProdutoRepository;
    private final LinhaService linhaService;
    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;
    private final ProdutoService produtoService;

    public ProdutoCSV(ProdutoService produtoService, IProdutoRepository iProdutoRepository, LinhaService linhaService, CategoriaService categoriaService, FornecedorService fornecedorService, ProdutoDTO produtoDTO) {
        this.iProdutoRepository = iProdutoRepository;
        this.categoriaService = categoriaService;
        this.linhaService = linhaService;
        this.fornecedorService = fornecedorService;
        this.produtoService = produtoService;
    }

    public void exportarCSV(HttpServletResponse resposta) throws Exception {
        String arquivo = "produtos.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter escritor = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(escritor).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"codigo_produto", "nome_produto", "unidades_por_caixa", "preço", "peso_unidade", "codigo_linha", "nome_linha", "codigo_categoria", "nome_categoria", "cnpj_fornecedor", "razão_fornecedor", "validade", "id", "linha"};
        icsvWriter.writeNext(tituloCSV);

        for (Produto linhas : iProdutoRepository.findAll()) {
            icsvWriter.writeNext(new String[]{
                    linhas.getCodigoProduto(), //Codigo
                    linhas.getNomeProduto(), //Nome do Produto
                    String.valueOf(linhas.getUnidade()), //Unidades
                    String.valueOf(linhas.getPrecoProduto()),
                    linhas.getPeso() + linhas.getUnidadePeso(), //Peso
                    linhas.getLinha().getCodigoLinha(), //Codigo da Linha
                    linhas.getLinha().getNomeLinha(), //Nome da Linha
                    linhas.getLinha().getCategoriaLinha().getCodigoCategoria(), //Codigo da Categoria
                    linhas.getLinha().getCategoriaLinha().getNomeCategoria(), //Nome da Categoria
                    linhas.getLinha().getCategoriaLinha().getFornecedor().getCnpj(), //CNPJ do Fornecedor
                    linhas.getLinha().getCategoriaLinha().getFornecedor().getRazao(), //Razão do Fornecedor
                    String.valueOf(linhas.getValidade()),
                    String.valueOf(linhas.getIdProduto()),
                    String.valueOf(linhas.getLinha())
            });
        }
    }

    public void importarProdutoPorFornecedor(long idFornecedor, MultipartFile multipartFile) throws Exception {

        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());

        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

        List<String[]> totalLido = csvReader.readAll();
        List<Produto> result = new ArrayList<>();

        for (String[] linhas : totalLido) {
            try {
                String[] dado = linhas[0].replaceAll("\"", "").split(";");

                ProdutoDTO produtoDTO = new ProdutoDTO();
                Produto produto = new Produto();
                Categoria categoria = new Categoria();
                Linha linha = new Linha();

                if (fornecedorService.existsById(idFornecedor)) {
                    if (!categoriaService.existsByCodigoCategoria(dado[7]))
                        categoria.setCodigoCategoria(dado[7]);
                    categoria.setNomeCategoria(dado[8]);
                    categoria.setFornecedor(fornecedorService.findByFornecedorCnpj(dado[9].replaceAll("[^0-9]", "")));
                    categoriaService.saveCat(categoria);
                } else if (categoriaService.existsByCodigoCategoria(dado[7])) {
                    categoria = categoriaService.existsByCategoriaLinha(dado[7]);
                    Optional<CategoriaDTO> categoriaOptional = Optional.ofNullable(this.categoriaService.findById(categoria.getIdCategoria()));

                    if (categoriaOptional.isPresent()) {
                        LOGGER.info("Executando update em id: [{}]", categoria.getIdCategoria());

                        categoria.setCodigoCategoria(dado[7]);
                        categoria.setNomeCategoria(dado[8]);
                        categoria.setFornecedor(fornecedorService.findByFornecedorCnpj(dado[9].replaceAll("[^0-9]", "")));
                        categoriaService.saveCat(categoria);
                    }
                }
                if (!linhaService.existsByCodigoLinha(dado[5])) {
                    linha.setCodigoLinha(dado[5]);
                    linha.setNomeLinha(dado[6]);
                    linha.setCategoriaLinha(categoriaService.findByCodigoCategoria(dado[7]));
                    linhaService.saveLin(linha);
                } else if (linhaService.existsByCodigoLinha(dado[5])) {
                    linha = linhaService.findByLinhaCodigoLinha(dado[5]);

                    Optional<Linha> linhaOptional = Optional.ofNullable(this.linhaService.findById(linha.getId()));
                    if (linhaOptional != null) {
                        Linha linhaExiste = linhaOptional.get();
                        LOGGER.debug("Payload: {}", linha);
                        LOGGER.info("Recebendo Up to Date de id [{}]", linha.getId());

                        linha.setCodigoLinha(dado[5]);
                        linha.setNomeLinha(dado[6]);
                        linha.setCategoriaLinha(categoriaService.findByCodigoCategoria(dado[7]));
                        linhaService.saveLin(linha);
                    }
                }
                produto.setCodigoProduto(dado[0]);
                produto.setNomeProduto(dado[1]);
                produto.setUnidade(Long.parseLong(dado[2]));
                produto.setPrecoProduto(Double.parseDouble(dado[3]));
                produto.setPeso(Double.parseDouble(dado[4].replaceAll("[^0-9.]", "")));
                produto.setValidade(LocalDate.parse(dado[11]));
                produto.setUnidadePeso(dado[4].replaceAll("[^A-Za-z]", ""));
                linha = linhaService.findByLinhaCodigoLinha(dado[5]);
                produto.setLinha(linha);

                if (iProdutoRepository.existsByCodigoProduto(produto.getCodigoProduto()) && idFornecedor == produto.getLinha().getCategoriaLinha().getFornecedor().getIdFornecedor()) {
                    produto.setIdProduto(produtoService.findByCodigoProduto(produto.getCodigoProduto()).getIdProduto());
                    produtoService.update(ProdutoDTO.of(produto), produto.getIdProduto());
                } else if (idFornecedor == produto.getLinha().getCategoriaLinha().getFornecedor().getIdFornecedor()) {
                    produtoDTO.setIdProduto(produto.getIdProduto());
                    produtoDTO.setCodigoProduto(produto.getCodigoProduto());
                    produtoDTO.setNomeProduto(produto.getNomeProduto());
                    produtoDTO.setLinha(produto.getLinha().getId());
                    produtoDTO.setPrecoProduto(produto.getPrecoProduto());
                    produtoDTO.setUnidades(produto.getUnidade());
                    produtoDTO.setPeso(produto.getPeso());
                    produtoDTO.setUnidadePeso(produto.getUnidadePeso());
                    produtoDTO.setValidade(produto.getValidade());
                    produtoService.save(produtoDTO);
                } else {
                    LOGGER.info("Produto [{}] pertence a outro fornecedor", produto.getIdProduto());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Produto> importarCSV(MultipartFile importacao) throws Exception {
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
                String cod = dados[4];

                ProdutoDTO produtoDTO = new ProdutoDTO();
                Linha linhaK = new Linha();
                Categoria catK = new Categoria();
                Fornecedor forK = new Fornecedor();

                produto.setCodigoProduto(dados[0]); //
                produto.setNomeProduto(dados[1]); //
                produto.setUnidade(Long.parseLong(dados[2])); //
                produto.setPrecoProduto(Double.parseDouble(dados[3]));
                produto.setPeso(Double.parseDouble(dados[4].replaceAll("[^0-9.]", ""))); //
                produto.setUnidadePeso(dados[4].replaceAll("[^A-Za-z]", "")); //
                linhaK.setCodigoLinha(dados[5]);//
                linhaK.setNomeLinha(dados[6]);
                catK.setCodigoCategoria(dados[7]);
                catK.setNomeCategoria(dados[8]);
                forK.setCnpj(dados[9].replaceAll("[^0-9]", ""));
                forK.setRazao(dados[10]);
                produto.setValidade(LocalDate.parse(dados[11]));


                resultado.add(produto);
                System.out.println(produto);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iProdutoRepository.saveAll(resultado);

    }
}
