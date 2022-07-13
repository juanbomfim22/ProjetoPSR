package br.ufs.dcomp.projetopsr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;


// Esse código é padrão, não tem o que ficar pensando muito
// O que vai mudar são as subClasses que serão adicionadas

@Configuration
public class JacksonConfig {
	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
//				objectMapper.registerSubtypes(PagamentoComCartao.class); // Aqui
//				objectMapper.registerSubtypes(PagamentoComBoleto.class); // Aqui
			
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}