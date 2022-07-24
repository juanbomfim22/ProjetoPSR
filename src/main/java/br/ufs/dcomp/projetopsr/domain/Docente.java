package br.ufs.dcomp.projetopsr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

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

import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
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
	
	private String matricula;
	
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
	
	public boolean canWorkGiven(Integer horario, DiaDaSemana dia) {
		if(this.restricao == null || this.restricao.getRestricoesDeHorario() == null) return true; // não tem restricoes
		if(horario == null || dia == null) return true;
		for(Entry<DiaDaSemana, String> d : this.restricao.getRestricoesDeHorario().entrySet()) {
			// Se a string contém o número X. Ex: 3 
			// Inicio, meio ou fim
			// 3,4,5,... (true)
			// 4,5,6,3 (true)
			// 4,5,3,6 (true)
			// 4,5,33,55 (false)

			String regex = "(^"+horario+",)|(,"+horario+",)|(,"+horario+"$)";
			Pattern p = Pattern.compile(regex);
			if(d.getKey().equals(dia) &&  p.matcher(d.getValue()).find()) {
				return false;
			}
		}
		return true;
	}
	 
	
}
