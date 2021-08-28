package br.com.edson.cursoSpring.resources;

import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Pedido;
import br.com.edson.cursoSpring.model.dto.PedidoDTO;
import br.com.edson.cursoSpring.services.PedidoService;
import br.com.edson.cursoSpring.utils.CustomPageable;

@RestController
@RequestMapping("pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoServico;
	
	@GetMapping("{id}")
	public ResponseEntity<?> buscar(@PathVariable Integer id) throws ObjectNotFound{
		return ResponseEntity.ok(pedidoServico.buscar(id));
	}
	
	@GetMapping
	public ResponseEntity<?> buscar(@RequestParam(name = "page", defaultValue = "0") Integer page, //
			@RequestParam(name = "size", defaultValue = "2") Integer size, //
			@RequestParam(name = "orderBy", defaultValue = "instante") String orderBy, //
			@RequestParam(name = "direction", defaultValue = "DESC") String direction) throws ObjectNotFound{
		Page<Pedido> list = pedidoServico.buscar( CustomPageable.customPageable(page, size, orderBy, direction));
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping
	public ResponseEntity<?> insert( @RequestBody PedidoDTO pedidoDTO ) throws ObjectNotFound{
		Pedido pedido = pedidoServico.insert( pedidoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(pedido);
	}
	
	@GetMapping("valor-total-pedido")
	public ResponseEntity<?> valorTotalPedido(@RequestParam("clienteId") Integer clienteId, //
			@RequestParam("pedidoId") Integer pedidoId) throws ObjectNotFound{
		HashMap<String, Double> valorTotal = pedidoServico.valorTotalPedido(clienteId, pedidoId);
		return ResponseEntity.ok(valorTotal);
	}
	
	@GetMapping("sem-paginacao")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.pedidoServico.listar());
	}

}
