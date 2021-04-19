package br.com.edson.cursoSpring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.cursoSpring.model.Produto;
import br.com.edson.cursoSpring.services.ProdutoService;
import br.com.edson.cursoSpring.utils.CustomPageable;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("produtos")
public class ProdutoResource {
	
	@Autowired
	ProdutoService produtoServico;
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable(name = "id") Integer id) throws ObjectNotFoundException{
		final Produto produto = produtoServico.find(id);
		return ResponseEntity.ok(produto);
	}
	
	@GetMapping("nome-categorias")
	public ResponseEntity<?> findByNomeCategorias( //
			@RequestParam(name = "nome", required = false, defaultValue = "") String nome, //
			@RequestParam(name = "categoriasId") List<Integer> categoriasId, //
			@RequestParam(name = "page", defaultValue = "0") Integer page, //
			@RequestParam(name = "size", defaultValue = "24") Integer size, //
			@RequestParam(name = "order",defaultValue = "nome") String order, //
			@RequestParam(name = "direction",defaultValue = "ASC") String direction){
		final Page<Produto> api = produtoServico.findByNomeCategorias(nome, categoriasId, CustomPageable.customPageable(page, size, order, direction));
		return ResponseEntity.ok(api);
	}	
	
}
