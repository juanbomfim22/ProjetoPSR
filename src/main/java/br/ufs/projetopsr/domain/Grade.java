package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
//	@JsonIgnore
//	@OneToMany(mappedBy="grade")
//	private List<Turno> turnos = new ArrayList<>();
	
	@JsonManagedReference
	@ManyToMany
	@JoinTable(name= "GRADE_DOCENTE",
	joinColumns = @JoinColumn(name= "grade_id"),
	inverseJoinColumns = @JoinColumn(name= "docente_id"))
	private List<Docente> docentes = new ArrayList<>();
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name= "GRADE_DISCIPLINA",
	joinColumns = @JoinColumn(name= "grade_id"),
	inverseJoinColumns = @JoinColumn(name= "disciplina_id"))
	private List<Disciplina> disciplinas = new ArrayList<>();
		 
	public Grade(Integer id, String nome, Date horaCriacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.setHoraCriacao(horaCriacao);
	}
 
}
