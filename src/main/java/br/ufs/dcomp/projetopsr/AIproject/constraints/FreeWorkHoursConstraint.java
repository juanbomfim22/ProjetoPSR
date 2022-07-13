package br.ufs.dcomp.projetopsr.AIproject.constraints;

import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.StaffMember;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;

public class FreeWorkHoursConstraint<VAR extends TimeBox, VAL extends WorkingGroup> implements Constraint<VAR, VAL> {
	private List<VAR> scope;

	public FreeWorkHoursConstraint(VAR member) {
		this.scope = Arrays.asList(member);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		VAR time = getScope().get(0);
		VAL group = assignment.getValue(time);
		if (group.getMembers().isEmpty()) return true;
		for(StaffMember member : group.getMembers()) {
			if(!member.getFree().get(time.getTime()))
					return false;
		}
		return true;
	}

}
