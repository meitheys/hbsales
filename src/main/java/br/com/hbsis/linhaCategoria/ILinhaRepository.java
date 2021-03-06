package br.com.hbsis.linhaCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface ILinhaRepository extends JpaRepository<Linha, Long> {

    boolean existsByCodigoLinha(String codigoLinha);
    Optional<Linha> findByCodigoLinha(String codigoLinha);

}
