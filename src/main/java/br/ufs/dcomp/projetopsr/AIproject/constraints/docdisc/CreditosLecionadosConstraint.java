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
public class CreditosLecionadosConstraint<VAR extends DocenteVariable, VAL extends DisciplinaVariable>
		implements Constraint<VAR, VAL> {
	private VAR var1;
	private List<VAR> scope = new ArrayList<>();

	public CreditosLecionadosConstraint(VAR docenteVariable) {
		this.var1 = docenteVariable;
		scope.add(docenteVariable);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		VAL atribuida = assignment.getValue(var1);
		if(var1.getDocente().getRestricao() == null) return true;
		int creditos_doc = var1.getDocente().getRestricao().getCreditosLecionados();
		int carga_hor =  atribuida.getDisciplina().getCargaHoraria();

		if (var1.getCreditosAtuais() + carga_hor > creditos_doc) {
			return false;
		}
		else {
			var1.setCreditosAtuais(var1.getCreditosAtuais() + carga_hor);
			return true;
		}

	}
}
