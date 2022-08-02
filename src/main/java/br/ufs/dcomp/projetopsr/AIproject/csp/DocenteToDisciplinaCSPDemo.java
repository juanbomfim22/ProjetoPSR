package br.ufs.dcomp.projetopsr.AIproject.csp;

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
import br.ufs.dcomp.projetopsr.AIproject.variables.DisciplinaVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.DocenteVariable;
import br.ufs.dcomp.projetopsr.domain.Disciplina;
import br.ufs.dcomp.projetopsr.domain.Docente;

public class DocenteToDisciplinaCSPDemo {

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

//		doc1.setRestricao(new Restricao(null, null, 0, null));
//		doc2.setRestricao(new Restricao(null, null, 0, null));
//		doc3.setRestricao(new Restricao(null, null, 0, null));
//		doc4.setRestricao(new Restricao(null, null, 0,null));
//		doc5.setRestricao(new Restricao(null, null, 0,null));

		// Linked list porque ArraysAsList cria uma coṕia fixa
		List<DocenteVariable> oferta_doc = new LinkedList<>(Arrays.asList(doc1, doc2, doc3, doc4, doc5, doc6, doc7, doc8).stream().map(x->new DocenteVariable(x)).collect(Collectors.toList()));
		List<DisciplinaVariable> oferta_dis = new LinkedList<>(Arrays.asList(d1, d2, d3, d4, d5, d6).stream().map(x->new DisciplinaVariable(x)).collect(Collectors.toList()));
		List<DisciplinaVariable> oferta_obr = new LinkedList<>(Arrays.asList(d1, d2, d3, d4, d5, d6).stream().map(x->new DisciplinaVariable(x)).collect(Collectors.toList()));

		// O csp... a definir
		CspListener.StepCounter<DocenteVariable, DisciplinaVariable> stepCounter = new CspListener.StepCounter<>();

		
		int count = oferta_obr.size();
		oferta_obr.add(new DisciplinaVariable(new Disciplina(null, null, "A definir", 0, null)));
		boolean shouldBreak = false;
		while (count > 0) {

			// A primeira iteração é para ele definir os docentes as disciplians 
			CSP<DocenteVariable, DisciplinaVariable> csp = new DocenteToDisciplinaCSP(
					oferta_doc,oferta_dis,oferta_obr
					);

			// Atenção: O dominio deve ser reo

			CspSolver<DocenteVariable, DisciplinaVariable> solver;

			Optional<Assignment<DocenteVariable, DisciplinaVariable>> solution;

			solver = new MinConflictsSolver<>(100000);

			solver.addCspListener(stepCounter);

			stepCounter.reset();

			System.out.println("Scheduling (Minimum Conflicts)");

			solution = solver.solve(csp);
			Assignment<DocenteVariable, DisciplinaVariable> assignment = null;
			try {
				assignment = solution.get();
				System.out.println(assignment);
			} catch (Exception e) {
				System.err.println(e);
				break;
			}
			int turnoObrigatoriasSize = oferta_obr.size()-1; // -1 para remover o "A definir"

			for (int i = 0; i < oferta_doc.size(); i++) {
				DisciplinaVariable disciplinaAux = assignment.getValue(assignment.getVariables().get(i));
	            int creditos_lecionados = assignment.getVariables().get(i).getCreditosAtuais();
	            oferta_doc.get(i).setCreditosAtuais(creditos_lecionados);
	            oferta_doc.get(i).getLecionadas().add(disciplinaAux);
 

				/*
				 * O que esse laço faz: 
				 * O algoritmo retornou um Assignment com cada professor a uma disciplina da lista de obrigatorias: 
				 * [IA, ES, PP1, PP2, PP3, PP4]
				 * EX: {Carlos=IA, Tarcisio=PP2, Leila=A definir, Beatriz=A definir, Débora=PP1, X=A definir, Y=PP4, Z=PP3}
				 * Porém, existem disciplinas obrigatorias que nao foram contempladas. Nesse caso, "ES"
				 * Então devo percorrer todo esse assignment e remover das disciplinas obrigatorias o que ja foi alocado
				 * Isso para que o algoritmo refaça a alocação com as disciplinas restantes
				 * Atenção: Isso permite que o professor pegue mais de uma materia
				 * TODO: Ver a questao da carga horaria que pode ser uma restrição
				 * 
				 */
				for (int j = 0; j < turnoObrigatoriasSize; j++) {
					// O primeiro condicional é para nao dar index error
					// O terceiro é pra vericar se nao é A definir 
					if (j < oferta_obr.size() && 
							disciplinaAux.equals(oferta_obr.get(j)) &&
							!disciplinaAux.getDisciplina().getCodigo().equals("A definir")
							) {
						oferta_obr.remove(j);
						j--;
					}
				}
			}
			System.out.println(stepCounter.getResults() + "\n");
						
			if(oferta_obr.size() == 1) {
				break;
			} 
		}
		
		
		
	}
}