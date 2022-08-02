package br.ufs.dcomp.projetopsr.AIproject.variables;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.projetopsr.domain.Disciplina;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplinaVariable extends Variable {
	private Disciplina disciplina; 

	public DisciplinaVariable(Disciplina disciplina) {
		super(disciplina.getCodigo());
		this.disciplina = disciplina;
	} 
}