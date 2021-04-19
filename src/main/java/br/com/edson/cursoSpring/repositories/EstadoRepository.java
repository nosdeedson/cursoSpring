package br.com.edson.cursoSpring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.edson.cursoSpring.model.Estado;
import br.com.edson.cursoSpring.model.dto.EstadoDTO;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	Optional<Estado> findByNome(String nomeEstado);	

	@Query("SELECT new br.com.edson.cursoSpring.model.dto.EstadoDTO("//
			+ " e.id, e.nome)" //
			+ " FROM Estado e " //
			+ " ORDER BY e.nome ")
	List<EstadoDTO> findAllByOrderByNome();

}
