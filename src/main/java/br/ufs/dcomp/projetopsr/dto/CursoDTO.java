package br.ufs.dcomp.projetopsr.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.domain.enums.CursoSigla;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Essas serão as credenciais passadas pelo caminho POST /login
// email e senha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

    @NotNull(message="Deve ter um valor")
    @Enumerated(EnumType.STRING)
 	private CursoSigla sigla;
	
    @NotNull(message="Deve ter um valor")
	private Integer periodo;
	
	
	
	public CursoDTO(Curso x) {
		this.id = x.getId();
		this.nome = x.getNome();
		this.sigla = x.getSigla();
		this.periodo = x.getPeriodo();
//		this.instituicaoDeEnsino = x.getInstituicaoDeEnsino();
	}
		
}
