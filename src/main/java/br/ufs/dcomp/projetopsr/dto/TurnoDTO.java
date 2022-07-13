package br.ufs.dcomp.projetopsr.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.ufs.dcomp.projetopsr.domain.Turno;
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

	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

//	@Pattern( regexp = "^\\d{2}:\\d{2}$", message="O horário deve estar no padrão HH:mm")
//	private Date horaInicio;
//	
//
//	@Pattern( regexp = "^\\d{2}:\\d{2}$", message="O horário deve estar no padrão HH:mm")
//	private Date horaTermino;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String horaInicio;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String horaTermino;

	public TurnoDTO(Turno x) {
		this.id = x.getId();
		this.nome = x.getNome();
//		this.horaInicio = x.getHoraInicio();
//		this.horaTermino = x.getHoraTermino();
	}
}
