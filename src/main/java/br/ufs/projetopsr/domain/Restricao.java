package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufs.projetopsr.domain.enums.DiaDaSemana;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Restricao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Integer diaDaSemana;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="docente_id")
	private Docente docente;
	
	public Restricao() {	
	}

	public Restricao(Integer id, String nome, DiaDaSemana diaDaSemana, Docente docente) {
		super();
		this.id = id;
		this.nome = nome;
		this.diaDaSemana = diaDaSemana.getId();
		this.setDocente(docente);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public DiaDaSemana getDiaDaSemana() {
		return DiaDaSemana.toEnum(diaDaSemana);
	}

	public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
		this.diaDaSemana = diaDaSemana.getId();
	}
	
	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restricao other = (Restricao) obj;
		return Objects.equals(id, other.id);
	}
	
}
