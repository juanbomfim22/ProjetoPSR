package br.ufs.dcomp.projetopsr.AIproject.csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.MinConflictsSolver;
import br.ufs.dcomp.projetopsr.AIproject.TableList;
import br.ufs.dcomp.projetopsr.AIproject.variables.DisciplinaVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.DocenteVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.HorarioVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.TurmaVariable;
import br.ufs.dcomp.projetopsr.domain.Disciplina;
import br.ufs.dcomp.projetopsr.domain.Docente;

public class HorarioToTurnoCSPDemo extends CSP<TurmaVariable, HorarioVariable>{
   
	public static void main(String[] args) throws Exception {
		Docente doc1 = new Docente(null, "Carlos");
		Docente doc2 = new Docente(null, "Tarcisio");
		Docente doc3 = new Docente(null, "Leila");
		Docente doc4 = new Docente(null, "Beatriz");
		Docente doc5 = new Docente(null, "Débora");
		Docente doc6 = new Docente(null, "X");
		Docente doc7 = new Docente(null, "Y");
		Docente doc8 = new Docente(null, "Z");

		Disciplina d1 = new Disciplina(null, "Inteligencia Artificial", "IA", 4, null);
		Disciplina d2 = new Disciplina(null, "Eng. Paralelas Software", "ES", 8, null);
		Disciplina d3 = new Disciplina(null, "Programacao Paralela 1", "PP1", 4, null);
		Disciplina d4 = new Disciplina(null, "Programacao Paralela 2", "PP2", 4, null);
		Disciplina d5 = new Disciplina(null, "Programacao Paralela 3", "PP3", 4, null);
		Disciplina d6 = new Disciplina(null, "Programacao Paralela 4", "PP4", 4, null);

		doc1.setPreferencias(Arrays.asList(d1, d2));
		doc2.setPreferencias(Arrays.asList(d3,d2, d4));
		doc3.setPreferencias(Arrays.asList(d6));
		
		List<DocenteVariable> oferta_doc = new LinkedList<>(Arrays.asList(doc1, doc2, doc3, doc4, doc5, doc6).stream().map(x->new DocenteVariable(x)).collect(Collectors.toList()));
		List<DisciplinaVariable> oferta_dis = new LinkedList<>(Arrays.asList(d1, d2, d3, d4, d5, d6).stream().map(x->new DisciplinaVariable(x)).collect(Collectors.toList()));
		List<DisciplinaVariable> oferta_obr = new LinkedList<>(Arrays.asList(d1, d2, d3, d4, d5, d6).stream().map(x->new DisciplinaVariable(x)).collect(Collectors.toList()));

		// O csp... a definir
		CspListener.StepCounter<TurmaVariable, List<HorarioVariable>> stepCounter = new CspListener.StepCounter<>();

		List<TurmaVariable> variables = new ArrayList<>();
		
		for (int i = 0; i < oferta_doc.size(); i++) { 
			TurmaVariable t = new TurmaVariable(oferta_dis.get(i), Arrays.asList(oferta_doc.get(i)));
			variables.add(t);
		}
		 
		CSP<TurmaVariable, List<HorarioVariable>> csp = new HorarioToTurnoCSP(variables);


		CspSolver<TurmaVariable, List<HorarioVariable>> solver;

		Optional<Assignment<TurmaVariable, List<HorarioVariable>>> solution;

		solver = new MinConflictsSolver<>(100000);

		solver.addCspListener(stepCounter);

		stepCounter.reset();

		System.out.println("Scheduling (Minimum Conflicts)");

		solution = solver.solve(csp);
		
		solution.ifPresent(x -> (new TableList()).printResults(x));
		Assignment<TurmaVariable, List<HorarioVariable>> assignment = null;
		
		try {
			assignment = solution.get();
//			System.out.println(assignment);
			System.out.println(stepCounter.getResults() + "\n");
		} catch (Exception e) {
			System.err.println(e); 
		}
		
	}
    
    
}

