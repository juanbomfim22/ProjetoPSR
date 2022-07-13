package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Turno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	 
	private String nome;
	

	//@JsonFormat(pattern="HH:mm")
	//private Date horaInicio;
	
	//@JsonFormat(pattern="HH:mm")
	//private Date horaTermino;
	
	@Min(0)
	@Max(24)
	private Integer qtdAulasDia;
	
	@Min(0)
	@Max(60)
	private Integer duracaoAula;

	@JsonFormat(pattern="HH:mm")
	private LocalTime horaInicio;
	
//	@JsonFormat(pattern="HH:mm")
//	private LocalTime horaTermino;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "instituicao_id")
	private Instituicao instituicao;

	@ElementCollection(fetch= FetchType.EAGER)
	private Set<DiaDaSemana> diasDaSemana = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy= "turno")
	private List<Docente> docentes = new ArrayList<>();

	
	@OneToMany(mappedBy= "turno")
	private List<Grade> grades = new ArrayList<>();
	
	public Turno(Integer id, String nome, Integer qtdAulasDia,  Integer duracaoAula, String horaInicio, Instituicao instituicao) {
		this.id = id;
		this.nome = nome;
		this.qtdAulasDia = qtdAulasDia;
		this.duracaoAula = duracaoAula;
		this.horaInicio = LocalTime.parse(horaInicio);
//		this.horaTermino = LocalTime.parse(horaTermino);
		this.instituicao = instituicao;
	}
}
