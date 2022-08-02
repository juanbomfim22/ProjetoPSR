package br.ufs.dcomp.projetopsr.AIproject.constraints.turmadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.HorarioVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.TurmaVariable;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;

/*
 * Ou seja, o docente nao pode dar duas aulas ao mesmo tempo no mesmo horario
 * 
 * 
*/

public class AulasParalelasConstraint<VAR extends TurmaVariable, VAL extends List<HorarioVariable>>
		implements Constraint<VAR, VAL> {
	private VAR var1;

	private List<VAR> scope = new ArrayList<>();

	public AulasParalelasConstraint(VAR docenteVariable) {
		this.var1 = docenteVariable;
		scope.add(docenteVariable);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		// Sao aulas paralelas aquelas espaçadas de 1 dia e na mesma faixa de horario
		// OBS: Caso a grade nao tenha dias igualmente espaçados entao vai considerar a grade como está
		/* ex: segunda e quinta sao aulas "paralelas" na grade composta por 4 dias
		 * Seg  Qua  Qui  Sex
		 *  o    |    o    |
		 */
		
		int quantidadeDeParalelas = var1.getDisciplina().getDisciplina().getCargaHoraria() / 2;
		VAL horarios = assignment.getValue(var1); 

		// TODO
		// Devem ser passados de forma dinamica na hora de criar a restricao
		List<DiaDaSemana> diasEscolhidos = Arrays.asList(DiaDaSemana.SEGUNDA, DiaDaSemana.QUARTA, 
				DiaDaSemana.QUINTA, DiaDaSemana.SEXTA);
		
		int rows = 5; 
		
		Map<DiaDaSemana, Integer> numeroDia = new HashMap<>();
		for(int i = 0; i < diasEscolhidos.size(); i++) {
			numeroDia.put(diasEscolhidos.get(i), i);
		}
		
		// Inicializando o map
		Map<Integer, List<DiaDaSemana>> horaDasAulasEmCadaDia = new HashMap<>();
		for (int i = 0; i < rows; i++) {
			// Cada array é uma lista de os horarios do dia da semana
			horaDasAulasEmCadaDia.put(i, Arrays.asList(new DiaDaSemana[DiaDaSemana.values().length]));
		}

		// Colocando cada um em um map
		for (HorarioVariable h : horarios) {
			List<DiaDaSemana> diasDeAula = horaDasAulasEmCadaDia.get(h.getTime()-1);
			diasDeAula.set(numeroDia.get(h.getDia()), h.getDia());
			horaDasAulasEmCadaDia.put(h.getTime()-1, diasDeAula);
		}
		
		// Checa se está intercalado um dia com um null
		// DIA, null, DIA, null, ... 
		for(List<DiaDaSemana> ls : horaDasAulasEmCadaDia.values()) {
			List<DiaDaSemana> listaDias = new ArrayList<>(ls);
			// Exatamente tres intercalados
			long numeroDeAulasNoDia = listaDias.stream()
	            .filter(c -> c != null)
	            .count();
			
			if(numeroDeAulasNoDia != quantidadeDeParalelas) {
				return false;
			} 
 
			/// Remove todos os nulos ate um dia encontrar
			DiaDaSemana first;
			do {

				first = listaDias.remove(0);
			}
			while(first == null);
			
			DiaDaSemana last;
			do {
				last = listaDias.remove(listaDias.size()-1);
			}
			while(last == null);
			
			long numeroDeNulosEntre = listaDias.stream()
		            .filter(c -> c == null)
		            .count();
			
			if(numeroDeNulosEntre != (quantidadeDeParalelas-1)) {
				return false;
			} 
		}
		return true;
	}
}
