package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufs.dcomp.projetopsr.domain.converters.DiaDaSemanaConverter;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Restricao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "restricao_id")
	private Integer id;
	
	// Atenção o horário é uma string de números
	// 1234 -- [1,2,3,4] , horaŕios
	
	@ElementCollection(fetch=FetchType.EAGER) 
	@Convert(attributeName = "key",converter = DiaDaSemanaConverter.class)
	private Map<DiaDaSemana, String> restricoesDeHorario = new HashMap<>();
	
	
//	@OneToMany(mappedBy = "restricao")
//	private List<Disciplina> preferencias = new ArrayList<>();
 
	@JsonIgnore  
	@OneToOne
	@JoinColumn(name="docente_id")
    @MapsId 
	private Docente docente;
	
	public Restricao() {
		
	}
	
	public Restricao(Integer id, Docente docente) {
		this.id = id;
		this.docente = docente;
	}

	
}
