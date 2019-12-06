package br.com.hbsis.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface IProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findById(Long codigo_produto);
}
