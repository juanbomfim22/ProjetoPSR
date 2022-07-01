package br.ufs.projetopsr.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.ufs.projetopsr.security.JWTAuthenticationFilter;
import br.ufs.projetopsr.security.JWTAuthorizationFilter;
import br.ufs.projetopsr.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**", 
			"/swagger-resources/**",
	        "/swagger-ui/**",
	        "/v2/api-docs",
	        "/webjars/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/docentes/**",
 			"/grades/**",
 			"/disciplinas/**",
 			"/cursos/**",
 			"/usuarios/**",
 			"/iclass/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/usuarios/**", 
			"/docentes/**",
 			"/grades/**",
 			"/disciplinas/**",
 			"/cursos/**",
			"/auth/forgot/**",
			"/login"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		
		// Por padrão, o CORS bloqueia acesso a fontes externas
		http.cors().and().csrf().disable(); // primeiro fazer o @Bean Cors..Source (lá embaixo) e depois adicionar essa linha
		

		http.authorizeRequests() //.antMatchers("/**").permitAll().anyRequest().authenticated();
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.anyRequest().authenticated();
//		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	// Permite o acesso a múltiplas fontes "/**" com configurações básicas
	@Bean
	CorsConfigurationSource corsConfigurationSource() { 
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	} 
}
