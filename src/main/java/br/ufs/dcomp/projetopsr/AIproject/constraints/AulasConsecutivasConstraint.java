package br.ufs.dcomp.projetopsr.AIproject.constraints;

import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;

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
	private VAL val;
	private Integer cargaHoraria =2;

	public AulasConsecutivasConstraint(VAR v1, VAR v2) {
		this.scope = Arrays.asList(v1, v2);
		this.val = val;
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
		
		VAR v1 = getScope().get(0);
		VAR v2 = getScope().get(1);
		VAL block1 = assignment.getValue(v1);
		VAL block2 = assignment.getValue(v2);
		if(block1.getDisciplina() == null || block2.getDisciplina() == null) return true;
		if(block1.getDisciplina() != null && 
				block1.getDisciplina().equals(block2.getDisciplina())
				&& block1.getDocentes().equals(block2.getDocentes())
				) {
			return true;
		}
		return false;
		
		// TODO: Restricao dois prof nao pode fazer a mesma disciplina (a menos que dentro do msm grp)
		// TODO: Restricao disciplina nao pode ter nulo depois se for considerar a carga
//		if(block1.getDisciplina() == null && block2.getDisciplina() == null) return true;
	
//		return false;
		
//		if(block1 ==0 null || block2 == null) return true;
//		
//		if(block1.getDisciplina() != null && block1.getDisciplina().equals(block2.getDisciplina())) {
//			return true;
//		}
//		return false;
	}

}
