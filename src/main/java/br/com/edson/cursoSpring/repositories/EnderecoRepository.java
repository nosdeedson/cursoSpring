package br.com.edson.cursoSpring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.edson.cursoSpring.model.Endereco;
import br.com.edson.cursoSpring.model.dto.EnderecoDTO;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
	
	@Query( "SELECT new br.com.edson.cursoSpring.model.dto.EnderecoDTO("
			+ "e.id, e.logradouro, e.numero, e.complemento, e.bairro,"
			+ " e.cep, e.cidade.nome, e.cidade.estado.nome)"
			+ " FROM Endereco e "
			+ " WHERE e.cliente.id= :clienteId"
			+ " ORDER BY e.logradouro" )
	List<EnderecoDTO> findByCliente_Id(@Param("clienteId") Integer clienteId);

}
