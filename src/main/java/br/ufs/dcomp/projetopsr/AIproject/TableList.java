package br.ufs.dcomp.projetopsr.AIproject;
 
import static br.ufs.dcomp.projetopsr.AIproject.csp.ScheduleCSP.scheduleSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_GridThemes;

public class TableList {
	public void printResults(Assignment<TimeBox, WorkingGroup> data) {		List<String> temp = new ArrayList<>();
		List<String> diasStr = Arrays.asList(DiaDaSemana.values()).stream().map(x -> x.getValor()).collect(Collectors.toList());
		diasStr.add(0, "");
		
		
//		Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(x);
//		while (matcher.find()) {
//			temp.add(String.join("<br>", matcher.group().replaceAll("[\\[\\]]", "").split(",")));
//		}
//		
		List<String> AMrow = new ArrayList<>(temp).stream().limit(scheduleSize/2).collect(Collectors.toList());
		AMrow.add(0, "AM");
		List<String> PMrow = new ArrayList<>(temp).stream().skip(scheduleSize/2).collect(Collectors.toList());

		PMrow.add(0, "PM");

		AsciiTable at = new AsciiTable();
		at.getContext().setGridTheme(TA_GridThemes.INSIDE_HORIZONTAL);
		at.addRule();
		at.addRow((Object[]) diasStr.toArray(new String[0]));
	
		List<TimeBox> vars = data.getVariables(); 
		List<DiaDaSemana> diasNaoRepetidos = new ArrayList<>();
		for(TimeBox t : vars) {
	
			if(!diasNaoRepetidos.contains(t.getDia())) {
				diasNaoRepetidos.add(t.getDia());
			} 
			else {
				break;
			}
		}
		int qtdAulasDia = diasNaoRepetidos.size() > 0 ? vars.size()/diasNaoRepetidos.size()
				: 0;

		
		for(int i = 0; i < qtdAulasDia; i++) {
			List<String> row = new ArrayList<>();
			row.add(0, "->");
			for(DiaDaSemana dia : DiaDaSemana.values()) {
				if(vars.size() > 0 && vars.get(0).getDia().equals(dia)) {
					TimeBox t = vars.remove(0);
					row.add(data.getValue(t).toString());
				} else {
					row.add("---");
				}
			}
			at.addRule();
			at.addRow((Object[]) row.toArray(new String[0]));
		} 
//		for(int i = 0; i < vars.size(); i++) {
//			TimeBox t = vars.remove(i);
//			if(t.getDia())
//		}
//		for(TimeBox box : data.getVariables()) {
//			
//			for(int i = 0; i < turno.getQtdAulasDia() * DiaDaSemana.values().length ; i++) {
//				
//			}
//		}
		at.addRule();

		System.out.println(at.render(100));
	}
}
