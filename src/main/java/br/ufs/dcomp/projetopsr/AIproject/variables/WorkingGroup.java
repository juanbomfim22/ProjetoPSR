package br.ufs.dcomp.projetopsr.AIproject.variables;

import java.util.List;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.projetopsr.domain.Docente;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// É o que pode colocar dentro de cada Timebox
// Uma disciplina com os membros (professores)
@Getter
@Setter
@ToString
public class WorkingGroup extends Variable {
	private List<Docente> members;
	
	public WorkingGroup(String name) {
		super(name);
	}
	
	public WorkingGroup(List<Docente> members) {
		this("");
		this.members = members;
	}

	@Override
	public String toString() {
		return members.toString();
	}
	
	 
}
