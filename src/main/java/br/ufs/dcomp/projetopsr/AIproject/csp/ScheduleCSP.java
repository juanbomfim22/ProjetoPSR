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
import java.util.stream.Stream;

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

		// Deve estar na ordem!

	 
	public static Turno turno = new Turno(null, null, 5, 50, LocalTime.parse("11:00"),
			EnumSet.of(DiaDaSemana.SEGUNDA, DiaDaSemana.QUARTA, DiaDaSemana.QUINTA, DiaDaSemana.SEXTA), null);
	
	public static final Integer scheduleSize = turno.getQtdAulasDia() * turno.getDiasDaSemana().size(); // Deve ser o
																										// produto
																										// aulasDia x
																										// qtdDias
//	public static final Integer aulasPorDia = 5;
//	public static final Integer diasDaSemana = 4;
//	public static final Integer cargaHoraria = 2;

	public static InputCSP input = new InputCSP();
	public static final Integer startTime = input.getStartTime();
	public static final Integer endTime = input.getEndTime();

//	public static final List<Disciplina> people = Arrays.asList(d1, d2, d3);

	/*
	 * --- Construção do domínio: Cada TimeBox pode assumir um desses valores ---
	 * Gera as combinações de docente e disciplinas que estao presentes no turno
	 * Exemplo:
	 * Carlos: [IA, ED]
	 * Tarcisio: [PP, SD]
	 * ==>
	 * Domínio: {[Carlos], IA}, {[Carlos], ED}, {[Tarcisio], PP}, {[Tarcisio], SD}
	 * 
	 * OBS: O nome entre [], porque permitem que seja mais de um professor dando a disciplina
	 * 
	 */

	// input.getPeople();
//	public static final Map<String, Disciplina> map = people.stream()
//			.collect(Collectors.toMap(Docente::getNome, x -> x));

	// Cria a grade preenchida 
	public static List<TimeBox> variables = range(turno);

	//TODO: Em vez de Arrays.asList(doc1,doc2,..) é turno.getDocentes
	// Em vez de Arryas.aslist(d1, d2, ..) é d.getDisciplinas
//	public static final List<WorkingGroup> groups = 
	
	// Gera todas as combinações de grupos de pessoas
//	public static final List<WorkingGroup> groups = Generator.subset(people).simple().stream()
//			.map(x -> new WorkingGroup("")).collect(Collectors.toList());

	/*
	 * Alice, Bob, ... Alice Bob, Alice Charlie, ... Alice Bob Charlie, ...
	 */

	/*
	 * Método que ajuda a criar a Grade de Horários vazia 
	 * | ----_ | ----- | ----- |
	 * | ----_ | ----- | ----- |
	 * | ----_ | ----- | ----- |
	 *        ...
	 */
	public static List<TimeBox> range(Turno turnoLocal) {
		
		/* Quero gerar
 		 * [TimeBox(time=1, dia=SEGUNDA), TimeBox(time=1, dia=QUARTA), TimeBox(time=1, dia=QUINTA), TimeBox(time=1, dia=SEXTA), 
 		 *  TimeBox(time=2, dia=SEGUNDA), TimeBox(time=2, dia=QUARTA), TimeBox(time=2, dia=QUINTA), TimeBox(time=2, dia=SEXTA), 
 		 *  TimeBox(time=3, dia=SEGUNDA), TimeBox(time=3, dia=QUARTA), TimeBox(time=3, dia=QUINTA), TimeBox(time=3, dia=SEXTA), 
 		 *  TimeBox(time=4, dia=SEGUNDA), TimeBox(time=4, dia=QUARTA), TimeBox(time=4, dia=QUINTA), TimeBox(time=4, dia=SEXTA), 
 		 *  TimeBox(time=5, dia=SEGUNDA), TimeBox(time=5, dia=QUARTA), TimeBox(time=5, dia=QUINTA), TimeBox(time=5, dia=SEXTA)]
		 */
		List<Integer> range = IntStream.rangeClosed(1, turnoLocal.getQtdAulasDia()).boxed().collect(Collectors.toList());
		List<Integer> dias = turnoLocal.getDiasDaSemana().stream().map(x -> x.getId()).collect(Collectors.toList());
		
		return Generator.cartesianProduct(range, dias).stream().map(x -> {
			DiaDaSemana d = DiaDaSemana.toEnum(x.get(1));
			return new TimeBox(x.get(0) + "," + d.getValor(), d);
		}).collect(Collectors.toList());
		
//			.stream().map(t -> t.g
//			;
//		 
//		return IntStream.rangeClosed(start, end).boxed()
//				.map(x -> generate(x, turnoLocal.getQtdAulasDia(), turnoLocal.getDiasDaSemana()))
//				.collect(Collectors.toList());
	}

	public static TimeBox generate(Integer x, Integer step, Set<DiaDaSemana> dias) {
		Integer diasCount = dias.size();
		Integer count = 0;
		for (int i = diasCount; i <= step * dias.size(); i += diasCount) {
			if (x <= i) {
				List<DiaDaSemana> ls = new ArrayList<>(dias);
				TimeBox t = new TimeBox(x + "", ls.get(x - (count * diasCount) - 1));
				return t;
			}
			count++;
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
			if (turno != null) {
				List<TimeBox> boxes = range(turno);
				for (DiaDaSemana dia : DiaDaSemana.values()) {
					Set<TimeBox> coluna = new HashSet<>();
					this.dias.put(dia, coluna);
				}
				boxes.stream().forEach(x -> {
					this.dias.get(x.getDia()).add(x);
				});
			}
		}
	}

	public ScheduleCSP() {
		super(variables);

		// As variáveis  
		 Usuario u1 = new Usuario(null, "juan", "juanbomfim21@gmail.com", null, null, ("123"));
		 Usuario u2 = new Usuario(null, "juan", "juan@teste2.com", null, null, ("123"));
		 Usuario u3 = new Usuario(null, "juan", "juan@teste3.com", null, null, ("123"));

		 Instituicao i1 = new Instituicao(null, "Univ", "UFS", "222", u1);

		 Curso c1 = new Curso(null, "Engenharia de Computacao", CursoSigla.EC, 6, i1);

		 Turno t1 = new Turno(null, null, 5, 50, LocalTime.parse("11:00"),
					EnumSet.of(DiaDaSemana.SEGUNDA, DiaDaSemana.QUARTA, DiaDaSemana.QUINTA, DiaDaSemana.SEXTA), null);

		 Turno t2 = new Turno(null, "tarde", 4, 50, LocalTime.parse("11:00"), null, null);
		 Turno t3 = new Turno(null, "noite", 4, 50, LocalTime.parse("11:00"), null, null);

		 Docente doc1 = new Docente(null, "Carlos", t1);
		 Docente doc2 = new Docente(null, "Tarcisio", t2);
		 Docente doc3 = new Docente(null, "Leila", t3);
		 Docente doc4 = new Docente(null, "Beatriz", t1);
		 
		 
		 Restricao r1a = new Restricao(null, doc1);
		 Restricao r2a = new Restricao(null, doc2);
		 Map<DiaDaSemana,String> a = new HashMap<>();
		 a.put(DiaDaSemana.QUARTA, "1,2,3");
		 a.put(DiaDaSemana.QUINTA, "1,2,3");
		 a.put(DiaDaSemana.DOMINGO, "1,2");
		 a.put(DiaDaSemana.SEGUNDA, "1,2,3");

		r1a.setRestricoesDeHorario(a);
			
		 Disciplina d1 = new Disciplina(null, "Inteligencia Artificial", "IA", 4, doc1);
		 Disciplina d2 = new Disciplina(null, "Eng Software", "ES", 8, doc2);
		 Disciplina d3 = new Disciplina(null, "Programacao Paralela", "PP", 4, doc2);
		
		 doc1.setDisciplinas(Arrays.asList(d1, d2));
		 doc2.setDisciplinas(Arrays.asList(d1, d3));
		 doc3.setDisciplinas(Arrays.asList(d3));
		 
		
			
		 doc1.setRestricao(r1a);
		 doc2.setRestricao(r1a);
//		 doc3.setRestricao(r1a);
		   

		 t1.setDocentes(Arrays.asList(doc1, doc2, doc3, doc4));
		

		 List<WorkingGroup> groups = Stream.concat(t1.getDocentes().stream()
					.flatMap(doc -> doc.getDisciplinas().stream()
							.map(di -> new WorkingGroup(di, Arrays.asList(doc))))
					, Arrays.asList(new WorkingGroup("")).stream()).collect(Collectors.toList());
//		 

		 // +1 para a entrada nula
		
		
		Domain<WorkingGroup> domain = new Domain<>(groups);

		for (TimeBox variable : getVariables()) {
			setDomain(variable, domain);
		}
		
		
		

		Integer s = t1.getDiasDaSemana().size();
			for(int i = 0; i < t1.getQtdAulasDia(); i++) {
				for(int j = 0; j < scheduleSize; j+=s) {
					if(i+j+s < scheduleSize) {
						TimeBox tb1 = getVariables().get(i+j);
						TimeBox tb2 = getVariables().get(i+j+s);
						addConstraint(new AulasConsecutivasConstraint<TimeBox, WorkingGroup>(tb1, tb2));
					}
				}
		}

//		for (DiaDaSemana dia : DiaDaSemana.values()) {
//			if (this.gradeHoraria.getDias().get(dia).isEmpty())
//				continue;
//			Set<TimeBox> set = this.gradeHoraria.getDias().get(dia);
//			Integer carga = 2;
//			addConstraint(new AulasConsecutivasConstraint<TimeBox, WorkingGroup>(new ArrayList<>(set), carga));
//		}

		for (TimeBox variable : getVariables()) {
//			addConstraint(new HorarioIndisponivelConstraint<TimeBox, WorkingGroup>(variable));

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
		 * Alice 2 1 2 3 4 5 true/false (0/1) Bob 2 1 2 3 4 5 true/false (0/1) ... Alice
		 * Bob Bob Eve
		 */
	}
}
