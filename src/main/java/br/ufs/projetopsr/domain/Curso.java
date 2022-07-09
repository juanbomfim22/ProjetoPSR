package br.ufs.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufs.projetopsr.domain.enums.CursoSigla;
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
	private Integer sigla;
	
	private Integer periodo;
	private String instituicaoDeEnsino;
	
	
	@JsonIgnore
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
		
}
