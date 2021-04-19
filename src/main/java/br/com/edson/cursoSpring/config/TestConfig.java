package br.com.edson.cursoSpring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import br.com.edson.cursoSpring.services.DBService;
import br.com.edson.cursoSpring.services.senderEmail.AbstractEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService db;
	
	@Bean
	public boolean instantiateDBService() throws ParseException {
		db.instantiateTestDBService();
		return true;
	}
	
	@Bean
	@Primary
	public AbstractEmailService criaEmail() {
		return new AbstractEmailService();
	}
	

}
