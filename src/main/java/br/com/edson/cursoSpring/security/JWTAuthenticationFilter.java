package br.com.edson.cursoSpring.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.edson.cursoSpring.model.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
    }
	
	@Override
	public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response)throws AuthenticationException  {
		
		try {
			
			CredenciaisDTO dto = new ObjectMapper()
					.readValue(request.getInputStream(), CredenciaisDTO.class);
			
			UsernamePasswordAuthenticationToken token = 
					new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha(), new ArrayList<>());
			
			Authentication auth = authenticationManager.authenticate(token);
			return auth;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	@Override
	public void successfulAuthentication( HttpServletRequest req, HttpServletResponse resp, FilterChain filter, Authentication auth) {
		String userName = ((UserSecurity) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(userName);
		resp.addHeader("Authorization","Bearer " + token);
		resp.addHeader("access-control-expose-headers", "Authorization");
	}
	
	
}

