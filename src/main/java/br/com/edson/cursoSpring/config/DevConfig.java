package br.com.edson.cursoSpring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.edson.cursoSpring.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService db;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String estrategiaBanco;

	@Bean
	public boolean instantiateDBService() throws ParseException {
		if ( !estrategiaBanco.equals("update")) {
			db.instantiateTestDBService();
			return true;
		}
		return false;
	}

}
