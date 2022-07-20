package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date horaCriacao;
	
	@ManyToOne
	@JoinColumn(name="turno_id")
	private Turno turno;
	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name="usuario_id")
//	private Usuario usuario;
	
//	@JsonIgnore
//	@OneToMany(mappedBy="grade")
//	private List<Turno> turnos = new ArrayList<>();
	 
//	@ManyToMany
//	@JoinTable(name= "GRADE_DOCENTE",
//	joinColumns = @JoinColumn(name= "grade_id"),
//	inverseJoinColumns = @JoinColumn(name= "docente_id"))
//	private List<Docente> docentes = new ArrayList<>();
//	
//	@JsonIgnore
//	@ManyToMany
//	@JoinTable(name= "GRADE_DISCIPLINA",
//	joinColumns = @JoinColumn(name= "grade_id"),
//	inverseJoinColumns = @JoinColumn(name= "disciplina_id"))
//	private List<Disciplina> disciplinas = new ArrayList<>();
		 
	public Grade(Integer id, String nome, Date horaCriacao, Turno turno) {
		super();
		this.id = id;
		this.nome = nome;
		this.horaCriacao = horaCriacao;
		this.turno = turno;
	}
 
}
