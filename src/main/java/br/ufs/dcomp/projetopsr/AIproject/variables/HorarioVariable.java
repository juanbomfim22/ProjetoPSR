package br.ufs.dcomp.projetopsr.AIproject.variables;


import aima.core.search.csp.Variable;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
 
@ToString
@Getter
@Setter
public class HorarioVariable extends Variable { 
	private Integer time;
	private DiaDaSemana dia;
	
    public HorarioVariable(String name, DiaDaSemana dia) {
		super(name); 
		
		if(name.equals("---")) return;
		this.setDia(dia);
		this.time = Integer.parseInt(name.split(",")[0]);
	}
    
    @Override
    public String toString() {
		return "("+ this.time + "," + this.dia +")";
    	
    }
 
}