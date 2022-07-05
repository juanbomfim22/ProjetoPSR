package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	
	private String nome;
	private String codigo;
	private Integer cargaHoraria; 
	 
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="docente_id")
	private Docente docente;
	 
	@JsonIgnore
	@ManyToMany(mappedBy="disciplinas")
	private List<Curso> cursos = new ArrayList<>();
	 
	@JsonIgnore
	@ManyToMany(mappedBy="disciplinas")
	private List<Grade> grades = new ArrayList<>();

	public Disciplina(Integer id, String nome, String codigo, Integer cargaHoraria, Docente docente) {
		super();
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
		this.docente = docente;
	}	
	
		
}
