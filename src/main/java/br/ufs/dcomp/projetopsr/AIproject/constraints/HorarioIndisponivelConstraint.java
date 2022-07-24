package br.ufs.dcomp.projetopsr.AIproject.constraints;

import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;
import br.ufs.dcomp.projetopsr.domain.Docente;

public class HorarioIndisponivelConstraint<VAR extends TimeBox, VAL extends WorkingGroup> implements Constraint<VAR, VAL> {
	private List<VAR> scope;

	public HorarioIndisponivelConstraint(VAR member) {
		this.scope = Arrays.asList(member);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		// Essa restricao vai para cada timeBox da grade
		for(VAR timeBox : getScope()) {
			VAL group = assignment.getValue(timeBox);
			if(group == null) return true;
			
			for(Docente d : group.getDocentes()) {
				return d.canWorkGiven(timeBox.getTime(), timeBox.getDia());
			} 
		}
		return true;
		 
//		VAL group = assignment.getValue(time);
//		if (group.getMembers().isEmpty()) return true;
//		for(StaffMember member : group.getMembers()) {
//			if(!member.getFree().get(time.getTime()))
//					return false;
//		} 
	}

}
