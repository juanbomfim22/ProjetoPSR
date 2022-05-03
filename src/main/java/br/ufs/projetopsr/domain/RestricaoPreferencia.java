package br.ufs.projetopsr.domain;

import javax.persistence.Entity;

import br.ufs.projetopsr.domain.enums.DiaDaSemana;

@Entity
public class RestricaoPreferencia extends Restricao {
	private static final long serialVersionUID = 1L;
	private Integer prioridade;
	
	public RestricaoPreferencia() {	
	}
	
	public RestricaoPreferencia(Integer id, String nome, DiaDaSemana diaDaSemana, Docente docente, Integer prioridade) {
		super(id, nome, diaDaSemana, docente);
		this.setPrioridade(prioridade);
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}
	
}
