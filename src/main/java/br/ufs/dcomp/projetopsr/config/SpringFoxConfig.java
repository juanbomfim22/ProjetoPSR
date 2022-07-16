package br.ufs.dcomp.projetopsr.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.fasterxml.classmate.TypeResolver;

import br.ufs.dcomp.projetopsr.dto.CredenciaisDTO;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration 
public class SpringFoxConfig { 
	
	@Bean
	public Docket swagger(TypeResolver typeResolver) {
		return new Docket(DocumentationType.SWAGGER_2)
				.additionalModels(
						typeResolver.resolve(CredenciaisDTO.class)
				 )
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.ufs.dcomp.projetopsr.resources")).paths(PathSelectors.any())
				
				.build().apiInfo(apiInfo()).useDefaultResponseMessages(false).globalResponses(HttpMethod.GET,
						Arrays.asList(new ResponseBuilder().code("200").description("200 message").build(),
								new ResponseBuilder().code("403").description("403 message").build(),
								new ResponseBuilder().code("500").description("500 message").build()))
				  .securityContexts(Arrays.asList(securityContext()))
			      .securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API - PSR", "Esta é a API para o projeto PSR", "Versão 1.0", "",
				new Contact(null, null, null), "", "", Collections.emptyList() // Vendor
																				// Extensions
		);
	}


	  private ApiKey apiKey() {
	    return new ApiKey("JWT", "Authorization", "header");
	  }

	  private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .build();
	  }

	  List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	  }
	  
}
