package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.ufs.projetopsr.domain.enums.CursoSigla;

@Entity
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	
	private String nome;
	private Integer sigla;
	private Integer periodo;
	private String instituicaoDeEnsino;
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name= "CURSO_DISCIPLINA",
		joinColumns = @JoinColumn(name= "curso_id"),
		inverseJoinColumns = @JoinColumn(name= "disciplina_id"))
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	public Curso(Integer id, String nome, CursoSigla sigla, Integer periodo, String instituicaoDeEnsino) {
		super();
		this.id = id;
		this.nome = nome;
		this.sigla = sigla.getId();
		this.periodo = periodo;
		this.instituicaoDeEnsino = instituicaoDeEnsino;
	}
	
	public Curso() {
		
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

	public CursoSigla getSigla() {
		return CursoSigla.toEnum(sigla);
	}

	public void setSigla(CursoSigla sigla) {
		this.sigla = sigla.getId();
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	public String getInstituicaoDeEnsino() {
		return instituicaoDeEnsino;
	}

	public void setInstituicaoDeEnsino(String instituicaoDeEnsino) {
		this.instituicaoDeEnsino = instituicaoDeEnsino;
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
		Curso other = (Curso) obj;
		return Objects.equals(id, other.id);
	}
		
}
