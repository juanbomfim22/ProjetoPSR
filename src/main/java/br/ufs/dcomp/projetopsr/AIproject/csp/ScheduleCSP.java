package br.ufs.dcomp.projetopsr.AIproject.csp;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.paukov.combinatorics3.Generator;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import br.ufs.dcomp.projetopsr.AIproject.IO.InputCSP;
import br.ufs.dcomp.projetopsr.AIproject.constraints.AllowVaccinatedConstraint;
import br.ufs.dcomp.projetopsr.AIproject.constraints.DependentMembersConstraint;
import br.ufs.dcomp.projetopsr.AIproject.constraints.FreeWorkHoursConstraint;
import br.ufs.dcomp.projetopsr.AIproject.constraints.OfficeHourConstraint;
import br.ufs.dcomp.projetopsr.AIproject.constraints.WorkLoadConstraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.StaffMember;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;

public class ScheduleCSP extends CSP<TimeBox, WorkingGroup> {
	public static final Integer scheduleSize = 24;

	public static InputCSP input = new InputCSP();
	public static final Integer startTime = input.getStartTime();
	public static final Integer endTime = input.getEndTime();

	public static final List<StaffMember> people = input.getPeople();
	public static final Map<String, StaffMember> map = people.stream()
			.collect(Collectors.toMap(StaffMember::getName, x -> x));

	public static final List<TimeBox> variables = IntStream.rangeClosed(1, scheduleSize).boxed()
			.map(x -> new TimeBox(x + "")).collect(Collectors.toList());

	public static final List<WorkingGroup> groups = Generator.subset(people).simple().stream()
			.map(x -> new WorkingGroup(x)).collect(Collectors.toList());

	/*
	 * Alice, Bob, ...
	 * Alice Bob, Alice Charlie, ...
	 * Alice Bob Charlie, ...
	 */
	public ScheduleCSP() {
		super(variables);

		Domain<WorkingGroup> domain = new Domain<>(groups);

		for (TimeBox variable : getVariables()) {
			setDomain(variable, domain);
		}

		for (TimeBox variable : getVariables()) {
			addConstraint(new FreeWorkHoursConstraint<TimeBox, WorkingGroup>(variable));
			addConstraint(new AllowVaccinatedConstraint<TimeBox, WorkingGroup>(variable));
			addConstraint(new OfficeHourConstraint<TimeBox, WorkingGroup>(variable, startTime, endTime));
		}

		for(StaffMember p1 : people) {
			for(StaffMember p2 : p1.getDependencies()) {
				addConstraint(new DependentMembersConstraint<TimeBox, WorkingGroup>(variables, p1, p2));	
			}
		}
		addConstraint(new WorkLoadConstraint<TimeBox, WorkingGroup>(variables, people));
		
		/*
		 * Alice 2 1 2 3 4 5 true/false (0/1) 
		 * Bob 2 1 2 3 4 5 true/false (0/1)
		 * ...
		 * Alice Bob
		 * Bob Eve
		 */
	}
}
