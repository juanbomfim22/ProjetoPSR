package br.ufs.dcomp.projetopsr.AIproject.constraints;

import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.StaffMember;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup; 

public class DependentMembersConstraint<VAR extends TimeBox, VAL extends WorkingGroup> implements Constraint<VAR, VAL> {
	private List<VAR> scope; 
	private StaffMember member1;
	private StaffMember member2;
	public StaffMember lastDependentMemberFound;
	public StaffMember actualDependentMemberFound;
	
	//membro 1 depende do membro 2
	public DependentMembersConstraint(List<VAR> scope, StaffMember member1, StaffMember member2) {	
		this.scope = scope; 
		this.member1 = member1;
		this.member2 = member2;
	}
	
	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		Integer flag = 0;
		
		for(VAR timeBox: getScope()) {
			VAL group = assignment.getValue(timeBox);
			if(group == null) return true;
			if (group.getMembers().contains(member1) || group.getMembers().contains(member2)) {
				if(group.getMembers().contains(member1)) {
					return false;
				}else {
					lastDependentMemberFound = actualDependentMemberFound = member2;
					break;
				}
			}
		}

		for(VAR timeBox : getScope()) { 
			VAL group = assignment.getValue(timeBox);
			if(group == null) return true;
			if(group.getMembers().isEmpty()) continue; 
			
			if (group.getMembers().contains(member1) && group.getMembers().contains(member2)) {
				return false;
			}
			
			if (group.getMembers().contains(member1) || group.getMembers().contains(member2)) {
				if(group.getMembers().contains(member1)) {
					actualDependentMemberFound = member1;
				}else {
					actualDependentMemberFound = member2;
				}
			}
			
			if(lastDependentMemberFound != actualDependentMemberFound) {
				flag = 1;
			}
			
			if(lastDependentMemberFound == actualDependentMemberFound && flag == 1) {
				return false;
			}
			
		}
		return true;
	}      
}
