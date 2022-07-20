package br.ufs.dcomp.projetopsr.AIproject.csp;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.paukov.combinatorics3.Generator;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import br.ufs.dcomp.projetopsr.AIproject.IO.InputCSP;
import br.ufs.dcomp.projetopsr.AIproject.constraints.AulasConsecutivasConstraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.TimeBox;
import br.ufs.dcomp.projetopsr.AIproject.variables.WorkingGroup;
import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.domain.Disciplina;
import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Instituicao;
import br.ufs.dcomp.projetopsr.domain.Restricao;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.domain.enums.CursoSigla;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import lombok.Data;

public class ScheduleCSP extends CSP<TimeBox, WorkingGroup> {
	
	
	static Usuario u1 = new Usuario(null, "juan", "juanbomfim21@gmail.com", null, null, ("123"));
	static Usuario u2 = new Usuario(null, "juan", "juan@teste2.com", null, null, ("123"));
	static Usuario u3 = new Usuario(null, "juan", "juan@teste3.com", null, null, ("123"));
	
	static Instituicao i1 = new Instituicao(null, "Univ", "UFS", "222", u1); 
	
	static Curso c1 = new Curso(null,"Engenharia de Computacao", CursoSigla.EC, 6, i1);
	
	static Turno t1 = new Turno(null, "manha",4, 50, LocalTime.parse("11:00"), EnumSet.of(DiaDaSemana.TERCA, DiaDaSemana.SEXTA), null);
	static Turno t2 = new Turno(null, "tarde",4, 50,LocalTime.parse("11:00"), null, null);
	static Turno t3 = new Turno(null, "noite", 4, 50, LocalTime.parse("11:00"), null, null);
	
	static Docente doc1 = new Docente(null, "Carlos", t1);
	static Docente doc2 = new Docente(null, "Tarcisio", t2);
	static Docente doc3 = new Docente(null, "Leila", t3);
	static Docente doc4 = new Docente(null, "Beatriz", t1);
	
	static Restricao r1a = new Restricao(null, doc1);
	static Restricao r2a = new Restricao(null, doc2);

	static Disciplina d1 = new Disciplina(null, "Inteligencia Artificial", "IA", 4, doc1);
	static Disciplina d2 = new Disciplina(null, "Eng Software", "ES", 8, doc2);
	static Disciplina d3 = new Disciplina(null, "Programacao Paralela", "PP", 4, doc2); 
	
	// Deve estar na ordem!
	
	
	public static Turno turno = new Turno(null, null, 5, 50, LocalTime.parse("11:00"), 
			EnumSet.of(DiaDaSemana.SEGUNDA, DiaDaSemana.QUARTA, DiaDaSemana.QUINTA, DiaDaSemana.SEXTA
					), null);
	
	public static final Integer scheduleSize = turno.getQtdAulasDia() * turno.getDiasDaSemana().size(); // Deve ser o produto aulasDia x qtdDias
//	public static final Integer aulasPorDia = 5;
//	public static final Integer diasDaSemana = 4;
//	public static final Integer cargaHoraria = 2;
	 
	public static InputCSP input = new InputCSP();
	public static final Integer startTime = input.getStartTime();
	public static final Integer endTime = input.getEndTime();

	public static final List<Disciplina> people = Arrays.asList(d1, d2, d3);
			//input.getPeople();
//	public static final Map<String, Disciplina> map = people.stream()
//			.collect(Collectors.toMap(Docente::getNome, x -> x));

	
	public static List<TimeBox> variables = range(1, scheduleSize, turno);

	// Gera todas as combinações de grupos de pessoas
	public static final List<WorkingGroup> groups = Generator.subset(people).simple().stream()
			.map(x -> new WorkingGroup("")).collect(Collectors.toList());

	/*
	 * Alice, Bob, ...
	 * Alice Bob, Alice Charlie, ...
	 * Alice Bob Charlie, ...
	 */
	
	public static List<TimeBox> range(Integer start, Integer end, Turno turnoLocal) {
		return IntStream.rangeClosed(start, end).boxed()
		.map(x -> 
			generate(x, turnoLocal.getQtdAulasDia(), turnoLocal.getDiasDaSemana())
		).collect(Collectors.toList()); 
	}
	
	public static TimeBox generate(Integer x, Integer step, Set<DiaDaSemana> dias) {
		Integer diasCount = dias.size();
		Integer count = 0; 
		for(int i=diasCount; i <= step*dias.size(); i += diasCount) {
			if(x <= i) {
				List<DiaDaSemana> ls = new ArrayList<>(dias);
				TimeBox t = new TimeBox(x+"", ls.get(x - (count*diasCount) -1));							
				return t;
			}
			count ++;
		} 
		return null;
	}
	
	private GradeHoraria gradeHoraria = new GradeHoraria(turno);
	 
	@Data
	public class GradeHoraria {
		private Turno turno;
		private Map<DiaDaSemana, Set<TimeBox>> dias = new HashMap<>();
		
		public GradeHoraria(Turno turno) {
			this.turno = turno;
			create();
		}
		
		public void create() {
			if(turno != null) {
				List<TimeBox> boxes = range(1, scheduleSize, turno);
				for(DiaDaSemana dia : DiaDaSemana.values()) {		
					Set<TimeBox> coluna = new HashSet<>(); 
					this.dias.put(dia, coluna);				
				}
				boxes.stream().forEach(x-> {
					this.dias.get(x.getDia()).add(x);
				});
			}
		}
	}
	
	public ScheduleCSP() {
		super(variables);
		
		

		Domain<WorkingGroup> domain = new Domain<>(groups);

		for (TimeBox variable : getVariables()) {
			setDomain(variable, domain);
		}

		
		
		for(DiaDaSemana dia : DiaDaSemana.values()) {
			if(this.gradeHoraria.getDias().get(dia).isEmpty()) continue;
			Set<TimeBox> set = this.gradeHoraria.getDias().get(dia);
			Integer carga = 2;
			addConstraint(new AulasConsecutivasConstraint<TimeBox, WorkingGroup>(new ArrayList<>(set), carga));
		}
		
		for (TimeBox variable : getVariables()) {
//			addConstraint(new FreeWorkHoursConstraint<TimeBox, WorkingGroup>(variable));
//			addConstraint(new AllowVaccinatedConstraint<TimeBox, WorkingGroup>(variable));
//			addConstraint(new OfficeHourConstraint<TimeBox, WorkingGroup>(variable, startTime, endTime));
		}
//
//		for(StaffMember p1 : people) {
//			for(StaffMember p2 : p1.getDependencies()) {
//				addConstraint(new DependentMembersConstraint<TimeBox, WorkingGroup>(variables, p1, p2));	
//			}
//		}
//		addConstraint(new WorkLoadConstraint<TimeBox, WorkingGroup>(variables, people));
		
		/*
		 * Alice 2 1 2 3 4 5 true/false (0/1) 
		 * Bob 2 1 2 3 4 5 true/false (0/1)
		 * ...
		 * Alice Bob
		 * Bob Eve
		 */
	}
}
