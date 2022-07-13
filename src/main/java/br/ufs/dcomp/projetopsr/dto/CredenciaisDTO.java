package br.ufs.dcomp.projetopsr.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Essas serão as credenciais passadas pelo caminho POST /login
// email e senha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
		
	@NotEmpty(message= "Preenchimento obrigatório")
	@Length(min = 8, message = "A senha deve ter mais de 8 caracteres")
	private String senha; 	
}
