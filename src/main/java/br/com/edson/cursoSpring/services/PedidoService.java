package br.com.edson.cursoSpring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.edson.cursoSpring.exceptions.AuthorizationException;
import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.Endereco;
import br.com.edson.cursoSpring.model.ItemPedido;
import br.com.edson.cursoSpring.model.Pagamento;
import br.com.edson.cursoSpring.model.PagamentoComBoleto;
import br.com.edson.cursoSpring.model.PagamentoComCartao;
import br.com.edson.cursoSpring.model.Pedido;
import br.com.edson.cursoSpring.model.Produto;
import br.com.edson.cursoSpring.model.dto.ItensDTO;
import br.com.edson.cursoSpring.model.dto.PedidoDTO;
import br.com.edson.cursoSpring.model.dto.PedidoDTOOut;
import br.com.edson.cursoSpring.model.enums.EstadoPagamento;
import br.com.edson.cursoSpring.model.enums.StatusPedidoEnum;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.repositories.EnderecoRepository;
import br.com.edson.cursoSpring.repositories.ItemPedidoRepository;
import br.com.edson.cursoSpring.repositories.PagamentoRepository;
import br.com.edson.cursoSpring.repositories.PedidoRepository;
import br.com.edson.cursoSpring.repositories.ProdutoRepository;
import br.com.edson.cursoSpring.services.senderEmail.EmailService;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private EmailService enviaEmail;
	
	@Autowired
	private UserService userLogado;
	
	
	public Pedido buscar(Integer id) throws ObjectNotFound {
		return pedidoRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFound("Pedido não encontrado"));
	}
	
	public Page<Pedido> buscar(Pageable pageable) throws ObjectNotFound{
		Cliente cliente = this.userLogado.authenticatedById().orElseThrow( () -> new AuthorizationException("Acesso negado."));
		
		Page<Pedido> list = pedidoRepository.findByCliente_Id(cliente.getId(), pageable);
		if ( list.isEmpty()) {
			throw new ObjectNotFound("Não há pedidos para este cliente.");
		}
		return list;
	}
	
	@Transactional
	public Pedido insert(PedidoDTO pedidoDTO) throws ObjectNotFound {
		
		Endereco entrega = enderecoRepository.findById(pedidoDTO.getEnderecoEntregaId())
				.orElseThrow( () -> new ObjectNotFound("Endereço inexistente."));
		
		Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
				.orElseThrow( () -> new ObjectNotFound("Cliente inexistente."));
		
		Pedido pedido = new Pedido(new Date(), entrega, cliente, StatusPedidoEnum.ABERTO);
		
		List<ItemPedido> itensPedido = new ArrayList<>();
		
		for ( ItensDTO itens : pedidoDTO.getItens()) {
			Produto produto = produtoRepository.findById(itens.getProdutoId())
					.orElseThrow( () -> new ObjectNotFound("Produto inexistente."));
			ItemPedido itemPedido = new ItemPedido(pedido,produto , 0.0, itens.getQuantidade(), produto.getPreco());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			itensPedido.add(itemPedido);
		}
		
		pedido.getItens().addAll(itensPedido);
		
		Pagamento pagto = null;
		
		if ( pedidoDTO.getTipoPagamento().equals("pagamentoComCartao")){
			pagto = new PagamentoComCartao(EstadoPagamento.PENDENTE.getCodigo(), pedido, pedidoDTO.getNumeroParcelas());
			pedido.setPagamento(pagto);
		}else {
			
			pagto = new PagamentoComBoleto(EstadoPagamento.PENDENTE.getCodigo(), pedido, null, LocalDateTime.now().plusDays(10L));
			pedido.setPagamento(pagto);
		}
		pagto.setPedido(pedido);
		pedidoRepository.save(pedido);
		itemPedidoRepository.saveAll(itensPedido);
		pagamentoRepository.save(pagto);
		enviaEmail.sendOrderConfirmationHtmlEmail(pedido);			
	
		return pedido;
	}
	
	public HashMap<String, Double> valorTotalPedido( Integer clienteId, Integer pedidoId) throws ObjectNotFound {
		
		clienteRepository.findById(clienteId).orElseThrow( () ->  new ObjectNotFound("O cliente com o id: " + clienteId + " não existe.") );
		
		pedidoRepository.findById(pedidoId).orElseThrow( () -> new ObjectNotFound("O pedido de id: " +  pedidoId + " não existe. ") );
		
		Optional<Double> total = pedidoRepository.valorTotalPedido(clienteId, pedidoId);
		HashMap<String, Double> valorTotal = new HashMap<>();
		if (total.isPresent()) {
			valorTotal.put("valorTotalPedido", total.get());
		}else {
			valorTotal.put("valor total", 0.0);
		}
		return valorTotal;
	}

	public List<PedidoDTOOut> listar(){
		return this.pedidoRepository.listarPedido();
	}
		
	
	
}
