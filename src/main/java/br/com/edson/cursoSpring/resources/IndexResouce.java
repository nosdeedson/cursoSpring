package br.com.edson.cursoSpring.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexResouce {

	@GetMapping
	public String IndexController() {
		return "request aceito";
	}
	
}
