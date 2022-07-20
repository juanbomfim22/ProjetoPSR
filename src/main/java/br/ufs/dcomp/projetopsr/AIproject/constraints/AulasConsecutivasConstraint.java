package br.ufs.dcomp.projetopsr.AIproject.constraints;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;
import br.ufs.dcomp.projetopsr.domain.Docente;

/**
 * A variável é cada caixinha, mas nessa restriçao
 * será passada a lista de caixinhas de uma coluna
 * de um dia
 *  Seg     Ter     Qua ...
 * |-----|------|------| ...
 * |-----|------|------| ...
 * |-----|------|------| ...
 *   ^  
 * 
 * A carga horaria é a quantidade de aulas consecutivas
 * @author juanb
 *
 * @param <VAR>
 * @param <VAL>
 */
public class AulasConsecutivasConstraint<VAR extends TimeBox, VAL extends WorkingGroup> implements Constraint<VAR, VAL> {
	private List<VAR> scope;
	private Integer cargaHoraria;

	public AulasConsecutivasConstraint(List<VAR> coluna, Integer cargaHoraria) {
		
		this.scope = new ArrayList<>(coluna);
		this.cargaHoraria = cargaHoraria;
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		/*
		 * O escopo é uma lista de TimeBox que devem ser verificados se possuem consecutivos
		 */
		for(VAR variable : getScope()) {
			VAL group = assignment.getValue(variable); 
			if(group == null || group.getMembers().isEmpty()) return false;
			if(group.getMembers().size() > 1) {
				for(Docente member : group.getMembers()) {
//					if(!member.isVaccinated())
//						return false;
				}
			}
		}
		return true;
	}

}
