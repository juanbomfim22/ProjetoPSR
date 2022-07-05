package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.ufs.projetopsr.domain.enums.TurnoDia;

@Entity
public class Docente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	private Integer turno;
	
	
	@OneToMany(mappedBy="docente",  cascade = CascadeType.ALL)
	private List<Restricao> restricoes = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="docentes")
	private List<Grade> grades = new ArrayList<>();

	
	@OneToMany(mappedBy="docente", cascade = CascadeType.ALL)
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	public Docente() {
	}

	public Docente(Integer id, String nome, TurnoDia turno) {
		super();
		this.id = id;
		this.nome = nome;
		this.turno = turno != null ? turno.getId() : null;
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

	public TurnoDia getTurno() {
		return TurnoDia.toEnum(turno);
	}

	public void setTurno(TurnoDia turno) {
		this.turno = turno.getId();
	}

	public List<Restricao> getRestricoes() {
		return restricoes;
	}

	public void setRestricoes(List<Restricao> restricoes) {
		this.restricoes = restricoes;
	}
	
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
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
		Docente other = (Docente) obj;
		return Objects.equals(id, other.id);
	}
}
