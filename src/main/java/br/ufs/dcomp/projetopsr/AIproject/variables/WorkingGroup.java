package br.ufs.dcomp.projetopsr.AIproject.variables;

import java.util.List;

import aima.core.search.csp.Variable;

public class WorkingGroup extends Variable {
	private List<StaffMember> members;
	public WorkingGroup(String name) {
		super(name);
	}
	
	public WorkingGroup(List<StaffMember> members) {
		this("");
		this.members = members;
	}
	
	public List<StaffMember> getMembers() {
		return members;
	}
	public void setMembers(List<StaffMember> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return members.toString();
	}
	
	

}
