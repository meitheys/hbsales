package br.com.hbsis.categoria;


import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorRepository;
import br.com.hbsis.fornecedor.FornecedorService;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final ICategoriaRepository iCategoriaRepository;
    private final FornecedorRepository iFornecedorRepository;
    private final FornecedorService fornecedorService;

    public CategoriaService(ICategoriaRepository iCategoriaRepository, FornecedorRepository iFornecedorRepository, FornecedorService fornecedorService) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.iFornecedorRepository = iFornecedorRepository;
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
        if (categoriaDTO.getFornecedor() == null){
            System.out.println("!!Fornecedor está vazio!!");
        }
        else{
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

    //findAll encontra as categorias nas List.

    public  List<Categoria> findAll(){
        return iCategoriaRepository.findAll();
    }

    public List<Categoria> readAll(Reader leitor) throws Exception{
        CSVReader ler = new CSVReader(leitor);
        List<Categoria> resultadoLeitura = new ArrayList<>();
        String[] linha;
        while((linha = ler.readNext()) != null) {
            Categoria categoria = new Categoria();

            FornecedorService fornecedorService = new FornecedorService(iFornecedorRepository);
            FornecedorDTO fornecedorDTO;
            Fornecedor fornecedor = new Fornecedor();

            fornecedorDTO = fornecedorService.findById(Long.parseLong(linha[3]));

            fornecedor.setRazao(fornecedorDTO.getRazao());
            fornecedor.setCnpj(fornecedorDTO.getCnpj());
            fornecedor.setNome(fornecedorDTO.getNome());
            fornecedor.setEndereco(fornecedorDTO.getNome());
            fornecedor.setTelefone(fornecedorDTO.getTelefone());
            fornecedor.setEmail(fornecedorDTO.getEmail());

            categoria.setFornecedor(fornecedor);
            categoria.setNomeCategoria(linha[2]);

            resultadoLeitura.add(categoria);
        }
        leitor.close();
        ler.close();
        return resultadoLeitura;
    }

    /*public List<Categoria> saveAll(List<Categoria> categorias) throws Exception{
        return iCategoriaRepository.saveAll(categorias);
    }*/


}