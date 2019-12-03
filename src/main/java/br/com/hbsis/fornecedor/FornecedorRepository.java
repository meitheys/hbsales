package br.com.hbsis.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe responsável pela comunciação com o banco de dados
 */
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    @Override
    boolean existsById(Long idFornecedor);
}
