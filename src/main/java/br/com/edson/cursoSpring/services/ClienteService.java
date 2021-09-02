package br.com.edson.cursoSpring.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.edson.cursoSpring.exceptions.AuthorizationException;
import br.com.edson.cursoSpring.exceptions.BusinessException;
import br.com.edson.cursoSpring.exceptions.CPF_Ou_CNPJUtilizado;
import br.com.edson.cursoSpring.exceptions.EmailUtilizado;
import br.com.edson.cursoSpring.exceptions.IntegridadeViolada;
import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.helper.ImageHelper;
import br.com.edson.cursoSpring.model.Cidade;
import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.Endereco;
import br.com.edson.cursoSpring.model.Pedido;
import br.com.edson.cursoSpring.model.dto.CadastroClienteDTO;
import br.com.edson.cursoSpring.model.dto.ClienteDTO;
import br.com.edson.cursoSpring.model.enums.PerfilEnum;
import br.com.edson.cursoSpring.model.enums.StatusPedidoEnum;
import br.com.edson.cursoSpring.repositories.CidadeRepository;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.repositories.EnderecoRepository;
import br.com.edson.cursoSpring.repositories.PedidoRepository;
import br.com.edson.cursoSpring.security.UserSecurity;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private S3Service s3Service;
	@Autowired
	private ImageHelper imgHelper;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;
	
	public List<Cliente> all() {
		return this.clienteRepository.findAll();
	}

	public void delete(Integer id) throws ObjectNotFound, IntegridadeViolada {

		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Cliente não encontrado."));
		if (clienteRepository.countPedidosByCliente_Id(cliente.getId(), StatusPedidoEnum.ABERTO.getCodigo()) == 0) {
			List<Pedido> pedidos = clienteRepository.findPedidosByCliente_Id(id,
					StatusPedidoEnum.FINALIZADO.getCodigo());
			for (Pedido pedido : pedidos) {
				pedido.setDeletado(true);
				pedido.setCliente(null);
				pedido.setEnderecoEntrega(null);
			}
			pedidoRepository.saveAll(pedidos);
			clienteRepository.deleteById(id);
		} else {
			throw new IntegridadeViolada("Cliente tem pedidos em aberto.");
		}
	}

	public Cliente find(Integer id) throws ObjectNotFound {
		UserSecurity user = UserService.authenticated();
		if (user == null || (!user.hasRole(PerfilEnum.ADMIN) && !id.equals(user.getId()))) {
			throw new AuthorizationException("Acesso negado.");
		}
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFound("Cliente não encontrada."));
	}

	public Cliente findByEmail(String email) throws ObjectNotFound {

		UserSecurity user = UserService.authenticated();
		if (!user.hasRole(PerfilEnum.ADMIN) && !user.getUsername().equals(email)) {
			throw new AuthorizationException("Acesso negado");
		}

		Cliente cliente = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFound("Cliente não encontrado."));
		return cliente;
	}
	
	public void novaSenha(String novaSenha) throws ObjectNotFound {
		UserSecurity user = UserService.authenticated();
		Cliente cliente = clienteRepository.findById(user.getId())
				.orElseThrow(() -> new ObjectNotFound("Usuário não encontrado."));
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		cliente.setSenha(encoder.encode(novaSenha));
		
		clienteRepository.save(cliente);		
	}

	@Transactional
	public ClienteDTO save(final CadastroClienteDTO objDto) throws EmailUtilizado, CPF_Ou_CNPJUtilizado {
		
		if( clienteRepository.existsByCpfOuCnpj(objDto.getCpfOuCnpj())) {
			throw new CPF_Ou_CNPJUtilizado("CPF ou CNPJ já utilizado.");
		}
		
		if (!objDto.getSenha().equals(objDto.getConfirmeSenha())) {
			throw new BusinessException("Senhas não conferem.");
		}
		Optional<Cliente> existe = clienteRepository.findByEmail(objDto.getEmail());

		if (existe.isPresent() ) {
			throw new EmailUtilizado("Email já usado");
		}

		Cliente cliente = objDto.toCliente();

		cliente = clienteRepository.save(cliente);

//		Optional<Estado> state = estadoRepository.findById(objDto.getEstadoId());
//		Estado estado = null;
//		if (state.isEmpty()) {
//			estado = new Estado(objDto.getEstado());
//			estado = estadoRepository.save(estado);
//		} else {
//			estado = state.get();
//		}
		Optional<Cidade> city = cidadeRepository.findById(objDto.getCidadeId());
//		Cidade cidade = null;
//		if (city.isEmpty()) {
//			cidade = new Cidade(objDto.getCidade(), estado);
//			cidadeRepository.save(cidade);
//		} else {
//			cidade = city.get();
//		}

		Endereco endereco = new Endereco(objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cliente, city.get());

		enderecoRepository.save(endereco);

		return new ClienteDTO(cliente);
	}

	@Transactional
	public void salvarAll(final List<Cliente> clientes) throws ObjectNotFound {
		try {
			clienteRepository.saveAll(clientes);
		} catch (Exception e) {
			throw new ObjectNotFound("Falha ao salvar as categorias.");
		}

	}

	public void update(ClienteDTO objDto, Integer id) throws ObjectNotFound, EmailUtilizado {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Cliente não encontrado."));

		Optional<Cliente> existe = clienteRepository.findByEmail(objDto.getEmail());

		if (existe.isPresent() && !cliente.getId().equals(existe.get().getId())) {
			throw new EmailUtilizado("Email já usado");
		}

		cliente.setNome(objDto.getNome());
		cliente.setEmail(objDto.getEmail());

		clienteRepository.save(cliente);
	}

	public URI uploadPicture(MultipartFile file) throws IOException, URISyntaxException, ObjectNotFound {

		UserSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado.");
		}

		Cliente cliente = clienteRepository.findById(user.getId())
				.orElseThrow(() -> new ObjectNotFound("Cliente não encontrado."));

		BufferedImage img = imgHelper.toBufferedImageFromMultPartFile(file);

		img = imgHelper.cropSquare(img);

		img = imgHelper.resize(img, size);

		String fileName = prefix + cliente.getId() + ".jpg";

		return s3Service.uploadFile(fileName, "image", imgHelper.toInputStreamFromBufferedImage(img));
	}

}
