package br.ufs.dcomp.projetopsr.AIproject;

import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.MinConflictsSolver;
import br.ufs.dcomp.projetopsr.AIproject.csp.ScheduleCSP;
import br.ufs.dcomp.projetopsr.AIproject.variables.HorarioVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.TurmaVariable;
 
public class ScheduleCSPDemo {
	
//	public static void PrintSolutionAssignmentTable(Optional<Assignment<HorarioVariable, TurmaVariable>> solution, CSP<HorarioVariable, TurmaVariable> csp) {
//		assignment = solution.get();
//		List<HorarioVariable> variables = csp.getVariables();
//		
//		List<StaffMember> members = new ArrayList<>();
//		for(TurmaVariable value: csp.getDomain(variables.get(0)))
//			if(value.getMembers().size() == 1)
//				members.add(value.getMembers().get(0));
//		
//		System.out.println("             1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24");
//		for(StaffMember p: members) {
//			if (p != null) {
//				System.out.printf("%s", p);
//				for(int j=0; j < 10-p.toString().length(); j++ ) {
//					System.out.printf(" ");
//				}
//				System.out.printf(":");
//				for(int i=0; i<24; i++){
//					TurmaVariable value = assignment.getValue(variables.get(i));
//					if(value.getMembers().contains(p)) {
//						System.out.printf("  %d", 1);
//					}else {
//						System.out.printf("  %d", 0);
//					}
//				}
//			}
//			System.out.println();
//		}
//	}
	
	public static Assignment<HorarioVariable, TurmaVariable> assignment = new Assignment<HorarioVariable, TurmaVariable>();
	
	public static void main(String[] args) { 
		CSP<HorarioVariable, TurmaVariable> csp = new ScheduleCSP();

		CspListener.StepCounter<HorarioVariable, TurmaVariable> stepCounter = new CspListener.StepCounter<>();

		CspSolver<HorarioVariable, TurmaVariable> solver;

		Optional<Assignment<HorarioVariable, TurmaVariable>> solution;

		solver = new MinConflictsSolver<>(100000);
//		solver = new FlexibleBacktrackingSolver<HorarioVariable, TurmaVariable>().setAll();
		
		solver.addCspListener(stepCounter);

		stepCounter.reset();

		System.out.println("Scheduling (Minimum Conflicts)");

		solution = solver.solve(csp);
		
		 
		
		solution.ifPresent(x -> (new TableList<HorarioVariable, TurmaVariable>()).printResults(x));

//		solution.ifPresent(System.out::println);
//		solution.ifPresent(System.out::println);
		System.out.println(stepCounter.getResults() + "\n");
//		PrintSolutionAssignmentTable(solution, csp);
		 
	}
}