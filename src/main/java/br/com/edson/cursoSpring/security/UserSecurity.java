package br.com.edson.cursoSpring.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.edson.cursoSpring.model.enums.PerfilEnum;

@Component
public class UserSecurity implements UserDetails {
	private static final long serialVersionUID = 1L;
		
	private Integer id;
	private String email;
	private String senha;
	
	private Collection<? extends GrantedAuthority> authorites;
	
	public UserSecurity() {}

	public UserSecurity(Integer id, String email, String senha, Set<PerfilEnum> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorites = perfis.stream()
				.map( perfil -> new SimpleGrantedAuthority(perfil.getDescricao()))
				.collect(Collectors.toList());
	}

	public boolean hasRole(PerfilEnum perfil) {
		return this.authorites.contains( new SimpleGrantedAuthority(perfil.getDescricao()));
	}
	
	public Integer getId() {
		return this.id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}
	
	@Override
	public String getPassword() {
		return senha;
	}

	/**
	 * userName é o email do userSecurity, convenção do Spring
	 */
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
}
