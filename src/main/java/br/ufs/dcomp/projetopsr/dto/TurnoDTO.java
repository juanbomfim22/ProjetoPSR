package br.ufs.dcomp.projetopsr.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Essas serão as credenciais passadas pelo caminho POST /login
// email e senha
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotNull(message = "Preenchimento obrigatório")
	@Min(0)
	@Max(24)
	private Integer qtdAulasDia;
	

	@NotNull(message = "Preenchimento obrigatório")
	@Min(0)
	@Max(60)
	private Integer duracaoAula;
	
	@NotNull(message = "Preenchimento obrigatório")
	@JsonFormat(pattern="HH:mm")
	private LocalTime horaInicio;
	
	@NotNull(message = "Preenchimento obrigatório")
	@ElementCollection(fetch= FetchType.EAGER)
	private Set<DiaDaSemana> diasDaSemana;

	public TurnoDTO(Turno x) {
		this.id = x.getId();
		this.nome = x.getNome();
		this.qtdAulasDia = x.getQtdAulasDia();
		this.duracaoAula = x.getDuracaoAula();
		this.horaInicio = x.getHoraInicio();
		this.diasDaSemana = x.getDiasDaSemana();
	}
}
