package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity 
public class Docente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "turno_id")
	private Turno turno;
	 
	@OneToOne(mappedBy="docente", cascade = CascadeType.ALL)
	private Restricao restricao; 
	
//	@JsonIgnore
//	@ManyToMany(mappedBy="docentes")
//	private List<Grade> grades = new ArrayList<>();

	@OneToMany(mappedBy="docente", cascade = CascadeType.MERGE)
	private List<Disciplina> disciplinas = new ArrayList<>();
	 
	public Docente(Integer id, String nome, Turno turno) {
		this.id = id;
		this.nome = nome;
		this.turno = turno;
	}

	@Override
	public String toString() {
		return nome;
	}
	 
	
}
