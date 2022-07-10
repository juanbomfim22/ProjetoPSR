package br.ufs.projetopsr.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.ufs.projetopsr.security.HttpCookieOAuth2AuthorizationRequestRepository;
import br.ufs.projetopsr.security.JWTAuthenticationFilter;
import br.ufs.projetopsr.security.JWTAuthorizationFilter;
import br.ufs.projetopsr.security.JWTUtil;
import br.ufs.projetopsr.security.oauth2.OAuth2AuthenticationSuccessHandler;
import br.ufs.projetopsr.services.CustomOAuth2UserService;

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
	
	@Autowired 
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**", 
			"/swagger-resources/**",
	        "/swagger-ui/**",
	        "/v2/api-docs",
	        "/webjars/**",
			"/oauth_login",
			"/login/**",
			"/loginSuccess",
			"/loginFailure",
 			"/iclass/**",
 			"/oauth2/authorization/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/docentes/**",
 			"/grades/**",
 			"/disciplinas/**",
 			"/cursos/**",
 			"/usuarios/**",
 			"/turnos/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/docentes/**",
 			"/grades/**",
 			"/disciplinas/**",
 			"/cursos/**",
 			"/usuarios/**",
 			"/turnos/**",
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
		

//		http.authorizeRequests()
//	      .antMatchers("/oauth_login")
//	      .permitAll()
//	      .anyRequest()
//	      .authenticated()
//	      .and()
//	      .oauth2Login()
//	      .loginPage("/oauth_login");
		
		http.formLogin().disable()
	        .httpBasic().disable()
	        .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
	        .and()
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS_GET).permitAll()
			.anyRequest().authenticated().and()
//            .defaultSuccessUrl("/loginSuccess")
//            .failureUrl("/loginFailure")
			.oauth2Login()
			.authorizationEndpoint()
			.authorizationRequestRepository(cookieAuthorizationRequestRepository())
			.and()
			.userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler);
	         
//		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
 
		// Teve que remover para funcionar o OAuth
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
		
	public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException)
				throws IOException, ServletException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
	                authException.getLocalizedMessage());
		}
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	 
    @Value("${app.cors.allowedOrigins}")
	private String[] allowed;
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedOrigins(Arrays.asList(allowed));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	} 
}
