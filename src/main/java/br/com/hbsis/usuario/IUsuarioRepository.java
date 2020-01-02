package br.com.hbsis.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
