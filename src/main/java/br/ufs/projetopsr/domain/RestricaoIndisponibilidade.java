package br.ufs.projetopsr.domain;

import javax.persistence.Entity;

import br.ufs.projetopsr.domain.enums.DiaDaSemana;

@Entity
public class RestricaoIndisponibilidade extends Restricao {
	private static final long serialVersionUID = 1L;
	private String motivo;
	
	public RestricaoIndisponibilidade() {
	}

	public RestricaoIndisponibilidade(Integer id, String nome, DiaDaSemana diaDaSemana, Docente docente, String motivo) {
		super(id, nome, diaDaSemana, docente);
		this.setMotivo(motivo);
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
}
