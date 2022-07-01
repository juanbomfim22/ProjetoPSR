package br.ufs.projetopsr.resources;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.dto.CredenciaisDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthApi {
	/**
	 * Implemented by Spring Security
	 */
	@ApiOperation(value = "Login", notes = "Login with the given credentials.")
	@ApiResponses({ @ApiResponse(code = 200, message = "", response = Authentication.class) })
	@PostMapping(value = "/LogiN", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void login(@RequestBody CredenciaisDTO cred) {
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
	
	@ApiOperation(value = "Logout", notes = "Logout the current user.")
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public void refreshToken(@RequestBody CredenciaisDTO cred) {
		throw new IllegalStateException("Add Spring Security to handle authentication");
	}
}