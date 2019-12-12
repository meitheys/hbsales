package br.com.hbsis.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe responsável pela comunciação com o banco de dados
 */
@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{

    boolean existsByCodigoCategoria(String codigoCategoria);
    Optional<Categoria> findByCodigoCategoria(String codigoCategoria);

    boolean existsByFornecedor(String fornecedor);
    Optional<Categoria> findByFornecedor(String fornecedor);
}
