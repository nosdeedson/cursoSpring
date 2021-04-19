package br.com.edson.cursoSpring.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edson.cursoSpring.model.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Integer>{

//	 neste caso o spring criou a query por conta do nome da função
	@Query("SELECT DISTINCT prod FROM Produto prod INNER JOIN prod.categorias cat "
			+ "WHERE prod.nome LIKE %:nome%  AND cat.id IN  :categoriasId")
	Page<Produto> findDistinctByCategorias_IdInAndNomeContaining( //
			@Param("categoriasId") List<Integer> categoriasId, //
			@Param("nome") String nome, @Param("pageRequest") Pageable pageRequest);
	
}
