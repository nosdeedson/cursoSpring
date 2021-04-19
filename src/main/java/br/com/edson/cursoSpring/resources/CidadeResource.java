package br.com.edson.cursoSpring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.dto.CidadeDTO;
import br.com.edson.cursoSpring.services.CidadeService;

@RestController
@RequestMapping("/estados/{estadoId}/cidades")
public class CidadeResource {

	@Autowired
	private CidadeService cidadeServico;
	
	@GetMapping	
	public ResponseEntity<?> findAll(@PathVariable(name = "estadoId") Integer estadoId) throws ObjectNotFound{
		List<CidadeDTO> cidades = cidadeServico.findAll(estadoId);
		return ResponseEntity.ok(cidades);
	}
}
