package br.ufs.dcomp.projetopsr.AIproject.constraints;

import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.StaffMember;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;

public class WorkLoadConstraint<VAR extends TimeBox, VAL extends WorkingGroup> implements Constraint<VAR, VAL> {
	private List<VAR> scope;
	private List<StaffMember> members;
	
	public WorkLoadConstraint(List<VAR> scope, List<StaffMember> members) {	
		this.scope = scope;
		this.members = members;
	}
	
	
	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
//		HashMap<StaffMember, Integer> map = new HashMap<>();
//		
//		for(StaffMember member : members) {
//			map.put(member, 0);
//		}
//		
//		for(VAR timeBox : getScope()) {
//			WorkingGroup value = assignment.getValue(timeBox);
//			if(value == null) return true;
//			for(StaffMember member: value.getMembers())
//				if(member != null)
//					map.put(member, map.get(member)+1);
//		}
//		
//		for (StaffMember member : members) {
//			if(member != null && member.getHour() != map.get(member)) 
//				return false;
//		} 
		return true;
	}      
}
