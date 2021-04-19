package br.com.edson.cursoSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.security.UserSecurity;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException(email));
		UserSecurity user = new UserSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
		return user;
	}

	
}
