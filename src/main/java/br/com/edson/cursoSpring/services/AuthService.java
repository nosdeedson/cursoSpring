package br.com.edson.cursoSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.EmailDTO;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.services.senderEmail.AbstractEmailService;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	AbstractEmailService emailSender;
	
	public void forgot(EmailDTO email) throws ObjectNotFound {
		Cliente cliente = clienteRepo.findByEmail(email.getEmail())
				.orElseThrow( () -> new ObjectNotFound("Email inexistente.") );
		
		String novaSenha = novaSenha();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		cliente.setSenha(encoder.encode(novaSenha));
		
		clienteRepo.save(cliente);
		
		emailSender.sendNewPassWordEmail( novaSenha);
		
	}

	private String novaSenha() {
		String novaSenha = Long.toHexString(Double.doubleToRawLongBits(Math.random()));
		return novaSenha;
	}
	
}
