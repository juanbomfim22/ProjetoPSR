package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Instituicao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name="instituicao_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	 
	private String nome;
	 
	private String sigla;
	
	private String cnpj;

	@JsonIgnore
	@MapsId
	@OneToOne
	@JoinColumn(name = "usuario_id") 
	private Usuario usuario;
	
	@JsonIgnore
	@OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
	private List<Turno> turnos = new ArrayList<>(); 
	
	@OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
	private List<Curso> cursos = new ArrayList<>();
	
	public Instituicao(Integer id, String nome, String sigla, String cnpj, Usuario usuario) {
		super();
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.cnpj = cnpj;
		this.usuario = usuario;
	}


}
