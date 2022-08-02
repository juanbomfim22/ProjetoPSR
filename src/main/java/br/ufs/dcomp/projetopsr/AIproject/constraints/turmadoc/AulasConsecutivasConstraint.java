package br.ufs.dcomp.projetopsr.AIproject.constraints.turmadoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

public class AulasConsecutivasConstraint<VAR extends TurmaVariable, VAL extends List<HorarioVariable>>
		implements Constraint<VAR, VAL> {
	private VAR var1;

	private List<VAR> scope = new ArrayList<>();

	public AulasConsecutivasConstraint(VAR docenteVariable) {
		this.var1 = docenteVariable;
		scope.add(docenteVariable);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		// As variáveis são as turmas (prof, disc)
		// Elas armazenam uma lista com X horários e cada Horário está presente em um
		// dia e hora
		// X é a qtd de creditos
		// var1 ==== turma
		// val1 ==== lista de horarios

		// O numero de aulas consecutivas é a metade da carga horaria (regra de negocio)
		// Deve ter no máximo X aulas consecutivas em um dia. Pode ter menos.
		// Ex: Carga: 4h, 2 consecutivas => máximo de 2 aulas por dia
		int aulasConsecutivas = var1.getDisciplina().getDisciplina().getCargaHoraria() / 2;
		VAL horarios = assignment.getValue(var1);
		int quantidadeDeDiasDiferentes = 0;

		// Inicializando o map
		Map<DiaDaSemana, List<Integer>> horaDasAulasEmCadaDia = new HashMap<>();
		for (DiaDaSemana dia : DiaDaSemana.values()) {
			horaDasAulasEmCadaDia.put(dia, new ArrayList<>());
		}

		// Colocando cada um em um map
		for (HorarioVariable h : horarios) {
 			List<Integer> horas = horaDasAulasEmCadaDia.get(h.getDia());
			horas.add(h.getTime());
			horaDasAulasEmCadaDia.put(h.getDia(), horas);
		}

		for (DiaDaSemana dia : DiaDaSemana.values()) {
			List<Integer> horas = horaDasAulasEmCadaDia.get(dia);
			if(horas.isEmpty()) continue;
			// Se as aulas nao sao consecutivas
			// Primeiro) calcular a media aritmetica e subtrair pelo primeiro
			// E ver se é igual a constante C
			// O valorde C é calculado:
			// 1 aula consecutiva => 0.0
			// 2 aulas consecutivas => 0.5
			// 3 aulas consecutivas => 1.0
			// 4 aulas consecutivas => 1.5
			// x aulas consecutivas => x/2 - 0.5
			// Segundo) Ver se no dia tem a mesma quantidade de disciplinas da materia

			int first = horas.get(0); // deve ser o menor
			double constante = ((double) aulasConsecutivas) / 2 - 0.5;
			
			double resultadoConta = horas.stream().collect(Collectors.averagingDouble(x -> x)) - first;
			
			// Se o resultadoConta for MENOR que constante, 
			// então tem menos aulas que o numero de consecutivas
			
			// Se o resultadoConta for MAIOR que constante,
			// entao tem mais aulas que o numero de consecutivas
			
			// Se for igual, então tem o mesmo numero de aulas consecutivas
			// na ordem consecutiva para o dia
			// Logo, se for diferente, entao viola a restricao
			if (resultadoConta  != constante || horas.size() != aulasConsecutivas) {
				return false;
			}
		}
		return true;

	}
}
