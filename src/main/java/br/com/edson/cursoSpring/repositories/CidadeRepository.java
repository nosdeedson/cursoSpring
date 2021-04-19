package br.com.edson.cursoSpring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.edson.cursoSpring.model.Cidade;
import br.com.edson.cursoSpring.model.dto.CidadeDTO;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	Optional<Cidade> findByNome( String cidadeNome);

	@Query( "SELECT new br.com.edson.cursoSpring.model.dto.CidadeDTO("
			+ " c.id, c.nome ) " //
			+ " FROM Cidade c " //
			+ " WHERE c.estado.id = :estadoId " //
			+ " ORDER BY c.nome" )
	List<CidadeDTO> findAllByEstado_IdOrderByNome(@Param("estadoId") Integer estadoId);
}
