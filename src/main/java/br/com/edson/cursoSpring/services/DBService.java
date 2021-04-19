package br.com.edson.cursoSpring.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.model.Categoria;
import br.com.edson.cursoSpring.model.Cidade;
import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.Endereco;
import br.com.edson.cursoSpring.model.Estado;
import br.com.edson.cursoSpring.model.ItemPedido;
import br.com.edson.cursoSpring.model.Pagamento;
import br.com.edson.cursoSpring.model.PagamentoComBoleto;
import br.com.edson.cursoSpring.model.PagamentoComCartao;
import br.com.edson.cursoSpring.model.Pedido;
import br.com.edson.cursoSpring.model.Produto;
import br.com.edson.cursoSpring.model.enums.EstadoPagamento;
import br.com.edson.cursoSpring.model.enums.PerfilEnum;
import br.com.edson.cursoSpring.model.enums.StatusPedidoEnum;
import br.com.edson.cursoSpring.model.enums.TipoCliente;
import br.com.edson.cursoSpring.repositories.CategoriaRepository;
import br.com.edson.cursoSpring.repositories.CidadeRepository;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.repositories.EnderecoRepository;
import br.com.edson.cursoSpring.repositories.EstadoRepository;
import br.com.edson.cursoSpring.repositories.ItemPedidoRepository;
import br.com.edson.cursoSpring.repositories.PagamentoRepository;
import br.com.edson.cursoSpring.repositories.PedidoRepository;
import br.com.edson.cursoSpring.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDBService() throws ParseException {

		Categoria cat1 = new Categoria("Informatica");
		Categoria cat2 = new Categoria("Escritório");
		Categoria cat3 = new Categoria("Cama mesa banho");
		Categoria cat4 = new Categoria("Perfumaria");
		Categoria cat5 = new Categoria("Papelaria");
		Categoria cat6 = new Categoria("Farmaceutico");
		Categoria cat7 = new Categoria("Textil");
		Categoria cat8 = new Categoria("Ferramentas");
		Categoria cat9 = new Categoria("Eletrônicos");

		Produto p1 = new Produto("Computador", 2000.0);
		Produto p2 = new Produto("Impressora", 800.0);
		Produto p3 = new Produto("Mouse", 80.0);
		Produto p4 = new Produto("Mesa de escritório", 300.00);
		Produto p5 = new Produto("Toalha", 50.00);
		Produto p6 = new Produto("Colcha", 200.00);
		Produto p7 = new Produto("TV true color", 1200.00);
		Produto p8 = new Produto("Roçadeira", 800.00);
		Produto p9 = new Produto("Abajour", 100.00);
		Produto p10 = new Produto("Pendente", 180.00);
		Produto p11 = new Produto("Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p11));
		cat5.getProdutos().addAll(Arrays.asList(p3));
		cat7.getProdutos().addAll(Arrays.asList(p5));
		cat8.getProdutos().addAll(Arrays.asList(p8));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat9));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat9));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat2, cat9));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3, cat7));
		p7.getCategorias().addAll(Arrays.asList(cat9));
		p8.getCategorias().addAll(Arrays.asList(cat8));
		p9.getCategorias().addAll(Arrays.asList(cat9));
		p10.getCategorias().addAll(Arrays.asList(cat9));
		p11.getCategorias().addAll(Arrays.asList(cat6));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9));
		// produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado("Minas Gerais");
		Estado est2 = new Estado("São Paulo");

		Cidade c1 = new Cidade("Uberlândia", est1);
		Cidade c2 = new Cidade("São Paulo", est2);
		Cidade c3 = new Cidade("Campinas", est2);

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Cliente cli1 = new Cliente("Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.setSenha(encoder.encode("1234"));
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93839383"));

		Endereco e1 = new Endereco("Rua Flores", "300", "APT 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco("Avenida Matos", "105", "sala 800", "Centro", "38777012", cli1, c2);
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		

		Cliente cli2 = new Cliente("Jose Silva", "Jose@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli2.addPerfil(PerfilEnum.ADMIN);
		cli2.setSenha(encoder.encode("1234"));
		cli2.getTelefones().addAll(Arrays.asList("27363323", "93839383"));
		Endereco e3 = new Endereco("Rua testes", "300", "APT 303", "Jardim", "38220834", cli2, c1);
		cli2.getEnderecos().addAll(Arrays.asList(e3));
	
		
		clienteRepository.save(cli1);
		clienteRepository.save(cli2);
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), e1, cli1, StatusPedidoEnum.FINALIZADO);
		Pedido ped2 = new Pedido(sdf.parse("10/10/2017 19:35"), e2, cli1, StatusPedidoEnum.ABERTO);

		Pagamento pagto1 = new PagamentoComCartao(EstadoPagamento.QUITADO.getCodigo(), ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE.getCodigo(), ped2, LocalDateTime.now(),
				null);

		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0D, 1, 2000D);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0D, 2, 80D);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100D, 1, 800D);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		/** Outros objetos**/
		
		Produto p12 = new Produto("Produto 12", 10.00);
		Produto p13 = new Produto("Produto 13", 10.00);
		Produto p14 = new Produto("Produto 14", 10.00);
		Produto p15 = new Produto( "Produto 15", 10.00);
		Produto p16 = new Produto( "Produto 16", 10.00);
		Produto p17 = new Produto( "Produto 17", 10.00);
		Produto p18 = new Produto( "Produto 18", 10.00);
		Produto p19 = new Produto( "Produto 19", 10.00);
		Produto p20 = new Produto("Produto 20", 10.00);
		Produto p21 = new Produto( "Produto 21", 10.00);
		Produto p22 = new Produto( "Produto 22", 10.00);
		Produto p23 = new Produto( "Produto 23", 10.00);
		Produto p24 = new Produto( "Produto 24", 10.00);
		Produto p25 = new Produto( "Produto 25", 10.00);
		Produto p26 = new Produto( "Produto 26", 10.00);
		Produto p27 = new Produto( "Produto 27", 10.00);
		Produto p28 = new Produto( "Produto 28", 10.00);
		Produto p29 = new Produto( "Produto 29", 10.00);
		Produto p30 = new Produto( "Produto 30", 10.00);
		Produto p31 = new Produto( "Produto 31", 10.00);
		Produto p32 = new Produto( "Produto 32", 10.00);
		Produto p33 = new Produto( "Produto 33", 10.00);
		Produto p34 = new Produto( "Produto 34", 10.00);
		Produto p35 = new Produto( "Produto 35", 10.00);
		Produto p36 = new Produto( "Produto 36", 10.00);
		Produto p37 = new Produto( "Produto 37", 10.00);
		Produto p38 = new Produto( "Produto 38", 10.00);
		Produto p39 = new Produto( "Produto 39", 10.00);
		Produto p40 = new Produto( "Produto 40", 10.00);
		Produto p41 = new Produto( "Produto 41", 10.00);
		Produto p42 = new Produto( "Produto 42", 10.00);
		Produto p43 = new Produto( "Produto 43", 10.00);
		Produto p44 = new Produto( "Produto 44", 10.00);
		Produto p45 = new Produto( "Produto 45", 10.00);
		Produto p46 = new Produto( "Produto 46", 10.00);
		Produto p47 = new Produto( "Produto 47", 10.00);
		Produto p48 = new Produto( "Produto 48", 10.00);
		Produto p49 = new Produto( "Produto 49", 10.00);
		Produto p50 = new Produto("Produto 50", 10.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
		p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
		p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));

		p12.getCategorias().add(cat1);
		p13.getCategorias().add(cat1);
		p14.getCategorias().add(cat1);
		p15.getCategorias().add(cat1);
		p16.getCategorias().add(cat1);
		p17.getCategorias().add(cat1);
		p18.getCategorias().add(cat1);
		p19.getCategorias().add(cat1);
		p20.getCategorias().add(cat1);
		p21.getCategorias().add(cat1);
		p22.getCategorias().add(cat1);
		p23.getCategorias().add(cat1);
		p24.getCategorias().add(cat1);

		p25.getCategorias().add(cat1);
		p26.getCategorias().add(cat1);
		p27.getCategorias().add(cat1);
		p28.getCategorias().add(cat1);
		p29.getCategorias().add(cat1);
		p30.getCategorias().add(cat1);
		p31.getCategorias().add(cat1);
		p32.getCategorias().add(cat1);
		p33.getCategorias().add(cat1);
		p34.getCategorias().add(cat1);
		p35.getCategorias().add(cat1);
		p36.getCategorias().add(cat1);
		p37.getCategorias().add(cat1);
		p38.getCategorias().add(cat1);
		p39.getCategorias().add(cat1);
		p40.getCategorias().add(cat1);
		p41.getCategorias().add(cat1);
		p42.getCategorias().add(cat1);
		p43.getCategorias().add(cat1);
		p44.getCategorias().add(cat1);
		p45.getCategorias().add(cat1);
		p46.getCategorias().add(cat1);
		p47.getCategorias().add(cat1);
		p48.getCategorias().add(cat1);
		p49.getCategorias().add(cat1);
		p50.getCategorias().add(cat1);

		produtoRepository.saveAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
		p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
		p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));

	}
}
