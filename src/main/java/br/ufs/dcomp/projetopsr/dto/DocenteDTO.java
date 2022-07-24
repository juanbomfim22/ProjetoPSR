package br.ufs.dcomp.projetopsr.dto;

import java.io.Serializable;

import javax.persistence.MapsId;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Restricao;
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

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	
	@Length(min=12, max=12, message="A matricula deve ter 12 dígitos, ex: 201900012345")
	private String matricula;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer turnoId;
	
	@MapsId
	private Restricao restricao;
	
	public DocenteDTO(Docente x) {
		this.id = x.getId();
		this.nome = x.getNome();
		this.turnoId = x.getTurno() != null ? x.getTurno().getId() : null; 
		this.restricao = x.getRestricao();
		this.matricula = x.getMatricula();
	}
		
}
