package br.com.hbsis.pedido;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.periodo.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByFuncionarioId(Long id);

    List<Pedido> findByPeriodo (Periodo periodo);
    List<Pedido> findByFuncionario (Funcionario funcionario);



}
