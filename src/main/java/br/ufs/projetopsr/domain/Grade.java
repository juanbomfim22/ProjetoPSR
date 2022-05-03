package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	private Date horaCriacao;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToMany
	@JoinTable(name= "GRADE_DOCENTE",
	joinColumns = @JoinColumn(name= "grade_id"),
	inverseJoinColumns = @JoinColumn(name= "docente_id"))
	private List<Docente> docentes = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name= "GRADE_DISCIPLINA",
	joinColumns = @JoinColumn(name= "grade_id"),
	inverseJoinColumns = @JoinColumn(name= "disciplina_id"))
	private List<Disciplina> disciplinas = new ArrayList<>();
		
	
	public Grade() {
	}

	public Grade(Integer id, String nome, Date horaCriacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.setHoraCriacao(horaCriacao);
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
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public Date getHoraCriacao() {
		return horaCriacao;
	}

	public void setHoraCriacao(Date horaCriacao) {
		this.horaCriacao = horaCriacao;
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
		Grade other = (Grade) obj;
		return Objects.equals(id, other.id);
	}
}
