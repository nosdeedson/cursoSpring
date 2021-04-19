package br.com.edson.cursoSpring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.cursoSpring.model.dto.EnderecoDTO;
import br.com.edson.cursoSpring.services.EnderecoService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("clientes/{clienteId}/enderecos")
public class EnderecoResource {
	
	@Autowired
	private EnderecoService enderecoService;
	
	@GetMapping
	public ResponseEntity<?> findAllEnderecoCliente(@PathVariable(name = "clienteId") Integer clienteId) throws ObjectNotFoundException{
		final List<EnderecoDTO>  enderecos = this.enderecoService.findAllEnderecoCliente(clienteId);
		return ResponseEntity.ok(enderecos);
	}
	
}
