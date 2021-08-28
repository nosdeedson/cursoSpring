package br.com.edson.cursoSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.edson.cursoSpring.services.DBService;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner {

//	@Autowired
//	private DBService db;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		db.instantiateTestDBService();
	}

}
