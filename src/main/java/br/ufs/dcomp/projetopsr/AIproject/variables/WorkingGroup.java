package br.ufs.dcomp.projetopsr.AIproject.variables;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.projetopsr.domain.Disciplina;
import br.ufs.dcomp.projetopsr.domain.Docente;
import lombok.Getter;
import lombok.Setter;

// É o que pode colocar dentro de cada Timebox
// Uma disciplina com os membros (professores)
@Getter
@Setter
public class WorkingGroup extends Variable {
	private Disciplina disciplina;
	private List<Docente> docentes;
	
	public WorkingGroup() {
		super("");
	}
	
	public WorkingGroup(String name) {
		super(name);
		this.docentes = new ArrayList<>();
	}
	
	public WorkingGroup(Disciplina disciplina, List<Docente> docentes) {
		this("");
		this.disciplina = disciplina;
		this.docentes = docentes;
	}

	@Override
	public String toString() {
		if(disciplina == null) {
			return "{}";
		}
		return "{" + docentes.toString() + ", " + disciplina.getCodigo() + "}";
	}
	
	 
}
