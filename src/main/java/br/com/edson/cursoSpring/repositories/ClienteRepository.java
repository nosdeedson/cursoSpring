package br.com.edson.cursoSpring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.Pedido;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	

	boolean existsByEmail(String email);
	
	Optional<Cliente> findByEmail(@Param("email") String email);
	
	boolean existsByCpfOuCnpj( String CPF_OU_CNPJ );
	
	@Query( "SELECT COUNT(p.id) " //
			+ " FROM Pedido p INNER JOIN Cliente c on p.cliente= c " //
			+ " WHERE c.id= :clienteId " //
			+ " and p.statusPedido= :codigoStatusPedido")
	Long countPedidosByCliente_Id( Integer clienteId, Integer codigoStatusPedido);
	
	@Query( " SELECT p FROM Pedido p INNER JOIN Cliente c ON p.cliente = c " //
			+ " WHERE p.cliente.id= :clienteId AND p.statusPedido= :codigoStatusPedido")
	List<Pedido> findPedidosByCliente_Id( Integer clienteId, Integer codigoStatusPedido);
	
}
