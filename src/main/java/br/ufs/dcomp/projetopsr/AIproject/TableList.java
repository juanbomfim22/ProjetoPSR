package br.ufs.dcomp.projetopsr.AIproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.projetopsr.AIproject.variables.HorarioVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.TurmaVariable;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_GridThemes;

public class TableList {

	public void printResults(Assignment<TurmaVariable, List<HorarioVariable>> data) {
		

//		Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(x);
//		while (matcher.find()) {
//			temp.add(String.join("<br>", matcher.group().replaceAll("[\\[\\]]", "").split(",")));
//		}

//		List<String> AMrow = new ArrayList<>(temp).stream().limit(scheduleSize/2).collect(Collectors.toList());
//		AMrow.add(0, "AM");
//		List<String> PMrow = new ArrayList<>(temp).stream().skip(scheduleSize/2).collect(Collectors.toList());
//
//		PMrow.add(0, "PM");

		AsciiTable at = new AsciiTable();
		
		List<String> temp = new ArrayList<>();
		List<String> diasStr = Arrays.asList(DiaDaSemana.values()).stream().map(x -> x.getValor())
				.collect(Collectors.toList());
		diasStr.add(0, "");
		at.getContext().setGridTheme(TA_GridThemes.INSIDE_HORIZONTAL);
		at.addRule();
		at.addRow((Object[]) diasStr.toArray(new String[0]));

		List<TurmaVariable> vars = data.getVariables();

		List<DiaDaSemana> diasNaoRepetidos = new ArrayList<>();

		/*
		 * Cada turma variable é o nome do professor, a disciplina e uma lista de
		 * horarios
		 */
//		for(TurmaVariable t : vars) {
//	
//			if(!diasNaoRepetidos.contains(t.getDia())) {
//				diasNaoRepetidos.add(t.getDia());
//			} 
//			else {
//				break;
//			}
//		}
		int qtdAulasDia = 5;
//				diasNaoRepetidos.size() > 0 ? vars.size()/diasNaoRepetidos.size()
//				: 0;
		Map<DiaDaSemana, List<List<TurmaVariable>>> map = new HashMap<>();

		// Cria o objecto que armazena todos os resultados
		for (DiaDaSemana dia : DiaDaSemana.values()) {
			List<TurmaVariable> arr = Arrays.asList(new TurmaVariable[qtdAulasDia]);
			List<List<TurmaVariable>> todas = arr.stream().map(turma -> {
				return new ArrayList<TurmaVariable>();
			}).collect(Collectors.toList());
			map.put(dia, todas);
		}

		// Organiza esses resultados para que cada dia tenha seu resultado
		for (TurmaVariable turma : vars) {
			for (HorarioVariable h : data.getValue(turma)) {
				List<List<TurmaVariable>> ls = map.get(h.getDia());
				ls.get(h.getTime() - 1).add(turma);
				map.put(h.getDia(), ls);
			}
		}

		// Organiza de forma a printar a tabela
		for (int i = 0; i < qtdAulasDia; i++) { // quantidade de linhas na tabela
			List<String> row = new ArrayList<>();
			row.add(0, "->");
			for(DiaDaSemana dia: DiaDaSemana.values()) {
				List<TurmaVariable> turmasDoHorario = map.get(dia).get(i);
				if(turmasDoHorario.isEmpty()) {
					row.add("---");
				} else {
					String joined = turmasDoHorario.stream()
							.map(TurmaVariable::toString).collect(Collectors.joining(", "));
					row.add(joined);
				} 
			}
			at.addRule();
			at.addRow((Object[]) row.toArray(new String[0]));
		}
		// Adicionar os dias na tabela

//				if(vars.size() > 0 && vars.get(0).get.equals(dia)) {
//					HorarioVariable t = vars.remove(0);
//					row.add(data.getValue(t).toString());
//				} else {
//					row.add("---");
//				}

		at.addRule();

		System.out.println(at.render(100));
	}
}
