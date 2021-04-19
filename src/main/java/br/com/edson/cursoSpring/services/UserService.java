package br.com.edson.cursoSpring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.security.UserSecurity;


@Component
@Scope("prototype")
public class UserService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	public static UserSecurity authenticated() {
		try {
			UserSecurity user = Optional.of((UserSecurity) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).orElseThrow( () -> new ObjectNotFound("Cliente não encontrado.") );
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Optional<Cliente> authenticatedById() {
		UserSecurity user = (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Cliente> usuario = clienteRepo.findById(user.getId());
		return usuario;
	}
	
}
