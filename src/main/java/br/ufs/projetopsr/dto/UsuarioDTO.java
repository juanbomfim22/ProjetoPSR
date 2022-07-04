package br.ufs.projetopsr.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.ufs.projetopsr.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Essas serão as credenciais passadas pelo caminho POST /login
// email e senha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	 
	@NotEmpty(message= "Preenchimento obrigatório")
	private String senha;
	
	public UsuarioDTO(Usuario x) {
		this.nome = x.getNome();
		this.email = x.getEmail();
//		this.senha = x.getSenha();
	}
		
}
