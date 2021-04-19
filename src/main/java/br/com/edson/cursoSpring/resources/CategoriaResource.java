package br.com.edson.cursoSpring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.edson.cursoSpring.exceptions.IntegridadeViolada;
import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Categoria;
import br.com.edson.cursoSpring.model.dto.CategoriaDTO;
import br.com.edson.cursoSpring.services.CategoriaService;
import br.com.edson.cursoSpring.utils.CustomPageable;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Integer id) throws ObjectNotFound, IntegridadeViolada{
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable(name="id") final Integer id) throws ObjectNotFound {
			Categoria categoria = categoriaService.buscar(id);
			return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping("page")
	public ResponseEntity<?> findAll( @RequestParam(name = "page", defaultValue = "0") Integer page, //
			@RequestParam(name = "size", defaultValue = "2") Integer size, //
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy, //
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) throws ObjectNotFound{
			final Page<CategoriaDTO> api = categoriaService.all(CustomPageable.customPageable(page, size, orderBy, direction));
			return ResponseEntity.ok(api);
	}
	
	@GetMapping("listar")
	public ResponseEntity<?> listarAll() throws ObjectNotFound{
		final List<CategoriaDTO> api = categoriaService.all();
		return ResponseEntity.ok(api);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> save(@Valid @RequestBody CategoriaDTO categoriaDto){
		Categoria categoria = categoriaDto.toEntity();
		categoria = categoriaService.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(categoria);
	}
	
	@RequestMapping(value = "lista", method = RequestMethod.POST)
	public ResponseEntity<List<Categoria>> saveAll(@RequestBody List<CategoriaDTO> categoriasDto) throws Exception{
		try {
			List<Categoria> categorias = categoriasDto.stream().map(item -> item.toEntity()).collect(Collectors.toList());
			categorias = categoriaService.salvarAll(categorias);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("r").build()
					.toUri();
			return ResponseEntity.created(uri).body(categorias);
		} catch (Exception e) {
			throw new Exception("Falha ao salvar");
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update( @Valid @RequestBody CategoriaDTO categoriaDto, @PathVariable Integer id) throws ObjectNotFound{
		Categoria categoria = categoriaDto.toEntity();
		categoria = categoriaService.update(categoria , id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
}
