package br.com.edson.cursoSpring.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amazonaws.services.codeartifact.model.HashAlgorithm;

import br.com.edson.cursoSpring.exceptions.CPF_Ou_CNPJUtilizado;
import br.com.edson.cursoSpring.exceptions.EmailUtilizado;
import br.com.edson.cursoSpring.exceptions.IntegridadeViolada;
import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.dto.CadastroClienteDTO;
import br.com.edson.cursoSpring.model.dto.ClienteDTO;
import br.com.edson.cursoSpring.services.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) throws ObjectNotFound, IntegridadeViolada {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable(value = "id") final Integer id) throws ObjectNotFound {
		Cliente cliente = clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	

	@GetMapping("email")
	public ResponseEntity<?> findByEmail(@RequestParam(name = "email") String email) throws ObjectNotFound{
		final Cliente cliente = clienteService.findByEmail(email);
		return ResponseEntity.ok(cliente);
	}

	@GetMapping("listar")
	public ResponseEntity<?> findAll() {
		try {
			final List<Cliente> api = clienteService.all();
			return ResponseEntity.ok(api);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping("novaSenha")
	public ResponseEntity<?> novaSennha( @RequestBody String novaSenha) throws ObjectNotFound{
		
		this.clienteService.novaSenha(novaSenha);
		return ResponseEntity.ok("Senha alterada com sucesso!");
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody CadastroClienteDTO objDto)
			throws CPF_Ou_CNPJUtilizado, EmailUtilizado {
		ClienteDTO cliente = clienteService.save(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();

		return ResponseEntity.created(uri).body(cliente);
	}

	@PostMapping("lista")
	public ResponseEntity<?> saveAll(@RequestBody final List<Cliente> clientes) {
		try {
			clienteService.salvarAll(clientes);
			return ResponseEntity.ok("Salvo com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id)
			throws ObjectNotFound, EmailUtilizado {
		clienteService.update(objDto, id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("uploadPicture")
	public ResponseEntity<?> uploadPicture(@RequestParam(name = "file") MultipartFile file)
			throws IOException, URISyntaxException, ObjectNotFound {
		URI uri = clienteService.uploadPicture(file);
		return ResponseEntity.created(uri).build();
	}

}
