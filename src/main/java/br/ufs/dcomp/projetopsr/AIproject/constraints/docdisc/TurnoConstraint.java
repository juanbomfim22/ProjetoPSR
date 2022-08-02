package br.ufs.dcomp.projetopsr.AIproject.constraints.docdisc;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.DisciplinaVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.DocenteVariable;

/**
 * A variável é cada caixinha, mas nessa restriçao será passada a lista de
 * caixinhas de uma coluna de um dia Seg Ter Qua ... |-----|------|------| ...
 * |-----|------|------| ... |-----|------|------| ... ^
 * 
 * A carga horaria é a quantidade de aulas consecutivas
 * 
 * @author juanb
 *
 * @param <VAR>
 * @param <VAL>
 */
public class TurnoConstraint<VAR extends DocenteVariable, VAL extends DisciplinaVariable>
		implements Constraint<VAR, VAL> {
	private VAR var1;
	private List<VAR> scope = new ArrayList<>(2);

	public TurnoConstraint(VAR docenteVariable) {
		this.var1 = docenteVariable;
		scope.add(docenteVariable);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		VAR docente = scope.get(0);
		VAL disciplina = assignment.getValue(docente);
		List<Integer> turnoIds = docente.getDocente().getTurnos().stream().map(x -> x.getHor)
		for()
		disciplina.getDisciplina().getCursos().get(0).get
		docente.getDocente().getTurnos().
		VAL value1 = assignment.getValue(var1);
		if(value1 == null || value2 == null) return true;
		boolean isSatisfied = !value1.equals(value2); 
		return isSatisfied;
	}

}
