package br.com.hbsis.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoProduto(String codigoProduto);
    Optional<Produto> findByCodigoProduto(String codigoProduto);
    boolean existsById(Long id);
    Optional<Produto> findById(Long id);
}
