package br.ufs.projetopsr.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.ufs.projetopsr.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, List.of(
					new ResponseBuilder().code("200")
					        .description("200 message").build(),
					new ResponseBuilder().code("403")
					        .description("403 message").build(),
				    new ResponseBuilder().code("500")
				        .description("500 message").build()
				));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("API - PSR",
				"Esta é a API para o projeto PSR", "Versão 1.0",
				"",
				new Contact(null,null,null),
				"", "", Collections.emptyList() // Vendor
																										// Extensions
		);
	}
}
