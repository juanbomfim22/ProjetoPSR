package br.ufs.dcomp.projetopsr.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.ufs.dcomp.projetopsr.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
//	
	@Autowired
	private DBService dbService;
//	
//	// O método @Bean vai estar disponível como componente para o sistema
//	// Se em outra classe, eu utilizo o @Autowired, o Spring vai procurar pelo componente
//	// (Que pode ser um @Bean) que devolve uma instância solicitada
//	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();		
		return true;
	} 
}
