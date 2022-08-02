package br.ufs.dcomp.projetopsr.AIproject.constraints.docdisc;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.DisciplinaVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.DocenteVariable;
import br.ufs.dcomp.projetopsr.domain.Disciplina;

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
public class DisciplinaPreferenciaConstraint<VAR extends DocenteVariable, VAL extends DisciplinaVariable>
		implements Constraint<VAR, VAL> {
	private VAR var1;
	private List<VAR> scope = new ArrayList<>();

	public DisciplinaPreferenciaConstraint(VAR docenteVariable) {
		this.var1 = docenteVariable;
		scope.add(docenteVariable);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		// Se o docente não tiver preferencia, então retorna true
		// Pois ele aloca primeiro as preferencias
		VAR docente = scope.get(0); 
		VAL value1 = assignment.getValue(var1); 
		for (Disciplina pref : docente.getDocente().getPreferencias()) {
			if(pref.equals(value1.getDisciplina())) {
				return true;
			}
		} 
		if(value1.getDisciplina().getCodigo().equals("A definir")) return true;
		return false;
	}

}
