package br.ufs.projetopsr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.ufs.projetopsr.domain.Docente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Essas serão as credenciais passadas pelo caminho POST /login
// email e senha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocenteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	 	 
	@NotNull(message= "Preenchimento obrigatório")
	private String turno;
	
	public DocenteDTO(Docente x) {
		this.id = x.getId();
		this.nome = x.getNome();
		this.turno = x.getTurno().getValor(); 
	}
		
}
