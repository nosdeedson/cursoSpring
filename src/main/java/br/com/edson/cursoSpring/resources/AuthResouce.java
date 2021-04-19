package br.com.edson.cursoSpring.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.EmailDTO;
import br.com.edson.cursoSpring.security.JWTUtil;
import br.com.edson.cursoSpring.security.UserSecurity;
import br.com.edson.cursoSpring.services.AuthService;
import br.com.edson.cursoSpring.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResouce {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService auth;
	
	@PostMapping("refresh-token")
	public ResponseEntity<?> refreshToken(HttpServletResponse response){
		UserSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return  ResponseEntity.noContent().build();
	}
	
	@PostMapping("forgot")
	public ResponseEntity<?> forgot( @RequestBody EmailDTO email) throws ObjectNotFound{
		auth.forgot(email);
		return ResponseEntity.ok("Uma nova senha foi enviada ao seu email.");
	}
	
	
	
	
	
	
	
	
}
