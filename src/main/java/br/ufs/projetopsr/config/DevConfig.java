package br.ufs.projetopsr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

//	@Autowired
//	private DBService dbService;

	// Pega a informação do application-dev.properties e coloca na variável strategy
//	@Value("${spring.jpa.hibernate.ddl-auto}")
//	private String strategy;
//
//	@Bean
//	public boolean instantiateDatabase() throws ParseException {
//		if (!"create".equals(strategy)) {
//			return false;
//		}
//		dbService.instantiateTestDatabase();
//		return true;
//
//	}
}
