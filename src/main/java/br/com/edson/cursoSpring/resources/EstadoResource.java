package br.com.edson.cursoSpring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.dto.EstadoDTO;
import br.com.edson.cursoSpring.services.EstadoService;

@RestController
@RequestMapping("estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public ResponseEntity<?> findAll() throws ObjectNotFound{
		List<EstadoDTO>  estados = estadoService.findAll();
		return ResponseEntity.ok(estados);
	}
	
}
