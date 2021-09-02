package br.com.edson.cursoSpring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edson.cursoSpring.model.Pedido;
import br.com.edson.cursoSpring.model.dto.PedidoDTOOut;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
//	@Query( "SELECT SUM(prod.preco + ip.desconto ) as total FROM Produto prod " //
//			+ " INNER JOIN ItemPedido ip ON ip.id.produto=prod " //
//			+ " INNER JOIN Pedido p ON ip.id.pedido=p " //
//			+ " WHERE p.id= :pedidoId AND p.cliente.id= :clienteId" )
	// esta query acima gasta mais processamento
	@Query( " SELECT SUM( item.preco * item.quantidade - item.desconto  ) FROM ItemPedido item  " //
			+ " WHERE item.id.pedido.id = :pedidoId AND item.id.pedido.cliente.id = :clienteId " )
	Optional<Double> valorTotalPedido( @Param("clienteId") Integer clienteId, @Param("pedidoId") Integer pedidoId );
	
	Page<Pedido> findByCliente_Id( @Param("clienteId") Integer clienteId, @Param("pageRequest") Pageable pageRequest );

	@Query(" SELECT new br.com.edson.cursoSpring.model.dto.PedidoDTOOut("
			+ " p.cliente.nome,"
			+ "	p.cliente.cpfOuCnpj,"
			+ " p.instante, "
			+ " p.itens"
			+ " )"
			+ " FROM Pedido p "
			+ " INNER JOIN ItemPedido ip "
			+ " ON ip.id.pedido = p")
//	@Query( " SELECT p.itens from Pedido p")
	List<PedidoDTOOut> listarPedido();
}




			
			
			