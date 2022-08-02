package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufs.dcomp.projetopsr.domain.enums.AuthProvider;
import br.ufs.dcomp.projetopsr.domain.enums.Perfil;
import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@Column(unique = true)
	private String email;
	
	private String imagemPerfil;
	
	@Column(nullable = false)
	private Boolean emailVerified = false;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	@JsonIgnore
	private String senha;

//	@JsonIgnore
//	@OneToMany(mappedBy = "usuario")
//	private List<Grade> grades = new ArrayList<>();
 
	
 
	
	
//	@OneToOne
//	@JoinColumn(name = "instituicao_id") 
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JoinColumn(name="instituicao_id")
	private Instituicao instituicao;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	public Usuario() {
	}

	public Usuario(Integer id, String nome, String email, String imagemPerfil, 
			String providerId, String senha, Instituicao instituicao) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.imagemPerfil = imagemPerfil;
		this.providerId = providerId;
		this.instituicao = instituicao;
		
		this.senha = senha;
		
		setProvider(AuthProvider.LOCAL);
		addPerfil(Perfil.CLIENTE);
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getId());
	}

}
