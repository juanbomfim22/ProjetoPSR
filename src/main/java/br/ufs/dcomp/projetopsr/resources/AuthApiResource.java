package br.ufs.dcomp.projetopsr.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.dcomp.projetopsr.dto.CredenciaisDTO;
import br.ufs.dcomp.projetopsr.repositories.UsuarioRepository;
import br.ufs.dcomp.projetopsr.security.JWTUtil;
import br.ufs.dcomp.projetopsr.security.UserPrincipal;
import br.ufs.dcomp.projetopsr.services.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthApiResource {
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private BCryptPasswordEncoder be;

	@Autowired
	private UsuarioResource resource;

	@ApiOperation(value = "Login", notes = "Login with the given credentials.")
	@ApiResponses({ @ApiResponse(code = 200, message = "", response = Authentication.class) })
	@ApiImplicitParams(value = 
			@ApiImplicitParam(dataTypeClass=CredenciaisDTO.class ,name = "Credenciais", value = "The body is a simple string", paramType = "body", required = true)
	)
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void login() {
		throw new IllegalStateException("Add Spring Security to handle authentication");
	}

	/**
	 * Implemented by Spring Security
	 */
	@ApiOperation(value = "Logout", notes = "Logout the current user.")
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout() {
		throw new IllegalStateException("Add Spring Security to handle authentication");
	}

	@ApiOperation(value = "Refresh token", notes = "Get a new JWT token")
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserPrincipal user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

}