package br.ufs.dcomp.projetopsr.AIproject.variables;


import aima.core.search.csp.Variable;

public class TimeBox extends Variable { 
	private Integer time;
	
    public TimeBox(String name) {
		super(name); 
		if(!name.matches("([1-9]|1[0-9]|2[0-4])")) {
			throw new IllegalArgumentException();
		}
		this.time = Integer.parseInt(name);
	}

	public Integer getTime() {
		return time;
	}
    
}