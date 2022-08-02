package br.ufs.dcomp.projetopsr.AIproject.constraints.turmadoc;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.HorarioVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.TurmaVariable;

/*
 * Ou seja, o docente nao pode dar duas aulas ao mesmo tempo no mesmo horario
 * 
 * 
*/

public class ExatamenteMetadeDaCargaConstraint<VAR extends TurmaVariable, VAL extends List<HorarioVariable>>
		implements Constraint<VAR, VAL> {
	private VAR var1;

	private List<VAR> scope = new ArrayList<>();

	public ExatamenteMetadeDaCargaConstraint(VAR docenteVariable) {
		this.var1 = docenteVariable;
		scope.add(docenteVariable);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) { 
//		int aulasConsecutivas = var1.getDisciplina().getDisciplina().getCargaHoraria() / 2;
//		VAL horarios = assignment.getValue(var1); 
//
//		// Inicializando o map
//		Map<DiaDaSemana, List<Integer>> horaDasAulasEmCadaDia = new HashMap<>();
//		for (DiaDaSemana dia : DiaDaSemana.values()) {
//			horaDasAulasEmCadaDia.put(dia, new ArrayList<>());
//		}
//
//		// Colocando cada um em um map
//		for (HorarioVariable h : horarios) {
// 			List<Integer> horas = horaDasAulasEmCadaDia.get(h.getDia());
//			horas.add(h.getTime());
//			horaDasAulasEmCadaDia.put(h.getDia(), horas);
//		}
//
//		for (DiaDaSemana dia : DiaDaSemana.values()) {
//			List<Integer> horas = horaDasAulasEmCadaDia.get(dia);
//			if(horas.isEmpty()) continue;
//			if (horas.size() != aulasConsecutivas) {
//				return false;
//			} 
//		}
		return true;

	}
}
