package br.ufs.projetopsr.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.ufs.projetopsr.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Essas serão as credenciais passadas pelo caminho POST /login
// email e senha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date horaCriacao;
	
	public GradeDTO(Grade x) {
		this.nome = x.getNome();
		this.horaCriacao = x.getHoraCriacao();
	}
}
