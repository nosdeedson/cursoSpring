package br.com.edson.cursoSpring.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class IndexResouce {

	@GetMapping
	public ModelAndView IndexController() {
		ModelAndView view = new ModelAndView();
		view.setViewName("index.html");
		return view;
	}
	
}
