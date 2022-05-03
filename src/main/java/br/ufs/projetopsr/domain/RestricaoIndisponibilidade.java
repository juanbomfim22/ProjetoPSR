package br.ufs.projetopsr.domain;

import javax.persistence.Entity;

import br.ufs.projetopsr.domain.enums.DiaDaSemana;

@Entity
public class RestricaoIndisponibilidade extends Restricao {
	private static final long serialVersionUID = 1L;
	private String razaoIndisponivel;
	
	public RestricaoIndisponibilidade() {
	}

	public RestricaoIndisponibilidade(Integer id, String nome, DiaDaSemana diaDaSemana, Docente docente, String razaoIndisponivel) {
		super(id, nome, diaDaSemana, docente);
		this.setrazaoIndisponivel(razaoIndisponivel);
	}

	public String getrazaoIndisponivel() {
		return razaoIndisponivel;
	}

	public void setrazaoIndisponivel(String razaoIndisponivel) {
		this.razaoIndisponivel = razaoIndisponivel;
	}
	
	
}
