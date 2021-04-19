package br.com.edson.cursoSpring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.model.Produto;
import br.com.edson.cursoSpring.repositories.ProdutoRepository;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	

	public Produto find(Integer id) throws ObjectNotFoundException {
		Optional<Produto> produto = produtoRepository.findById(id);
		Produto p = produto.get();
		if( p == null) {
			throw new ObjectNotFoundException("Produto não encontrado");
		}
		return produto.get();
	}
	
	public Page<Produto> findByNomeCategorias( String nome, List<Integer> categoriasId, Pageable pageRequest) {
		return produtoRepository.findDistinctByCategorias_IdInAndNomeContaining(categoriasId, nome, pageRequest);
	}

}
