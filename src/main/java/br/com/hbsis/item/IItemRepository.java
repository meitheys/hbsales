package br.com.hbsis.item;

import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM dbo.seg_item WHERE fk_id_pedido =:fkPedido", nativeQuery = true)
    List<Item> findFkProd(@Param("fkPedido") Long fkPedido);

    boolean existsByPedidoAndProduto(Pedido pedido, Produto idProduto);


    Optional<Item> findByPedidoAndProduto(Pedido pedido, Produto idProduto);

}

