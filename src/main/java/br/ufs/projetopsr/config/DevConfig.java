package br.ufs.projetopsr.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.ufs.projetopsr.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	// Pega a informação do application-dev.properties e coloca na variável strategy
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
//		if (!"create".equals(strategy)) {
//			return false;
//		}
		dbService.instantiateTestDatabase();
		return true;
	}
}
