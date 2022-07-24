package br.ufs.dcomp.projetopsr.AIproject;

import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspListener;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.MinConflictsSolver;
import br.ufs.dcomp.projetopsr.AIproject.csp.ScheduleCSP;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;
 
public class ScheduleCSPDemo {
	
//	public static void PrintSolutionAssignmentTable(Optional<Assignment<TimeBox, WorkingGroup>> solution, CSP<TimeBox, WorkingGroup> csp) {
//		assignment = solution.get();
//		List<TimeBox> variables = csp.getVariables();
//		
//		List<StaffMember> members = new ArrayList<>();
//		for(WorkingGroup value: csp.getDomain(variables.get(0)))
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
//					WorkingGroup value = assignment.getValue(variables.get(i));
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
	
	public static Assignment<TimeBox, WorkingGroup> assignment = new Assignment<TimeBox, WorkingGroup>();
	
	public static void main(String[] args) { 
		CSP<TimeBox, WorkingGroup> csp = new ScheduleCSP();

		CspListener.StepCounter<TimeBox, WorkingGroup> stepCounter = new CspListener.StepCounter<>();

		CspSolver<TimeBox, WorkingGroup> solver;

		Optional<Assignment<TimeBox, WorkingGroup>> solution;

		solver = new MinConflictsSolver<>(100000);
//		solver = new FlexibleBacktrackingSolver<TimeBox, WorkingGroup>().setAll();
		
		solver.addCspListener(stepCounter);

		stepCounter.reset();

		System.out.println("Scheduling (Minimum Conflicts)");

		solution = solver.solve(csp);
		
		 
		
		solution.ifPresent(x -> (new TableList()).printResults(x));

//		solution.ifPresent(System.out::println);
//		solution.ifPresent(System.out::println);
		System.out.println(stepCounter.getResults() + "\n");
//		PrintSolutionAssignmentTable(solution, csp);
		 
	}
}