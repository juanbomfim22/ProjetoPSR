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
public class DocentesDiferentesConstraint<VAR extends DocenteVariable, VAL extends DisciplinaVariable>
		implements Constraint<VAR, VAL> {
	private VAR var1;
	private VAR var2;
	private List<VAR> scope = new ArrayList<>(2);

	public DocentesDiferentesConstraint(VAR docenteVariable, VAR docenteVariable2) {
		this.var1 = docenteVariable;
		this.var2 = docenteVariable2; 
		scope.add(docenteVariable);
		scope.add(docenteVariable2);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		VAL value1 = assignment.getValue(var1);
		VAL value2 = assignment.getValue(var2);
		if(value1 == null || value2 == null) return true;
		if(value1.getName().equals("A definir")) return true;
		boolean isSatisfied = !value1.equals(value2); 
		return isSatisfied;
	}

}
