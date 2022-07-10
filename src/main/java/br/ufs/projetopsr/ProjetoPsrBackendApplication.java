package br.ufs.projetopsr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.ufs.projetopsr.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ProjetoPsrBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPsrBackendApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {

		
	}

}
