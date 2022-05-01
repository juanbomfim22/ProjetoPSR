package com.juanbomfim.projetopsr.domain;

import java.io.Serializable;
import java.util.Objects;

public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String sigla;
	private Integer periodo;
	
	public Curso(Integer id, String nome, String sigla, Integer periodo) {
		super();
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.periodo = periodo;
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
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
		Curso other = (Curso) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
