package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufs.dcomp.projetopsr.domain.enums.CursoSigla;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	 
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private CursoSigla sigla;
	
	private Integer periodo;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "instituicao_id")
	private Instituicao instituicao;
	

	@ManyToMany
	@JoinTable(name= "CURSO_DISCIPLINA_OBRIGATORIA",
		joinColumns = @JoinColumn(name= "curso_id"),
		inverseJoinColumns = @JoinColumn(name= "disciplina_id"))
	private List<Disciplina> disciplinasObrigatorias = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name= "CURSO_DISCIPLINA_OPTATIVA",
		joinColumns = @JoinColumn(name= "curso_id"),
		inverseJoinColumns = @JoinColumn(name= "disciplina_id"))
	private List<Disciplina> disciplinasOptativas = new ArrayList<>();
	
	public Curso(Integer id, String nome, CursoSigla sigla, Integer periodo, Instituicao instituicao) {
		super();
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.periodo = periodo;
		this.instituicao = instituicao;
	}
	
	public Curso() {
		
	}
		
}
