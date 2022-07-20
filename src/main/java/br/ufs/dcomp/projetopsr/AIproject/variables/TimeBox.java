package br.ufs.dcomp.projetopsr.AIproject.variables;


import aima.core.search.csp.Variable;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
 
@ToString
@Getter
@Setter
public class TimeBox extends Variable { 
	private Integer time;
	private DiaDaSemana dia;
	
    public TimeBox(String name, DiaDaSemana dia) {
		super(name); 
		
		if(name.equals("---")) return;
		if(!name.matches("\\d+")) {
			throw new IllegalArgumentException();
		}
		this.setDia(dia);
		this.time = Integer.parseInt(name);
	}
 
}