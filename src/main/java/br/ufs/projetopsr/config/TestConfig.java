package br.ufs.projetopsr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
//	
//	@Autowired
//	private DBService dbService;
//	
//	// O método @Bean vai estar disponível como componente para o sistema
//	// Se em outra classe, eu utilizo o @Autowired, o Spring vai procurar pelo componente
//	// (Que pode ser um @Bean) que devolve uma instância solicitada
//	
//	@Bean
//	public boolean instantiateDatabase() throws ParseException {
//		dbService.instantiateTestDatabase();		
//		return true;
//	}
//	
//	@Bean
//	public EmailService emailService() {
//		return new MockEmailService();
//	}
}
