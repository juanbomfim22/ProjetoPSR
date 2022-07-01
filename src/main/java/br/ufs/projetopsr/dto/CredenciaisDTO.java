package br.ufs.projetopsr.dto;

import java.io.Serializable;

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
	private String email;
	private String senha; 	
}
