package br.ufs.dcomp.projetopsr.AIproject.csp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.paukov.combinatorics3.Generator;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import br.ufs.dcomp.projetopsr.AIproject.constraints.turmadoc.AulasConsecutivasConstraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.HorarioVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.TurmaVariable;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;

/*
 * Cada turma pode assumir uma lista de horaros
 */
public class HorarioToTurnoCSP extends CSP<TurmaVariable, List<HorarioVariable>>{
    public List<TurmaVariable> turmas = new ArrayList<>(); 
    
    /*
     * Uma turma é um assignment do tipo {Docente, Disciplina}, Ex:
     * 	Turma 1 : { Carlos, IA } 
     * A turma pode ter mais de um professor, mas ainda nao foi implementado
     * A turma tem o dominio de horarios a depender da disciplina dela. 
     * Por exemplo, IA é 4 créditos, então uma restrição 
     */
    private Turno turno1 = new Turno(null, null, 5, 50, LocalTime.parse("11:00"),
			EnumSet.of(DiaDaSemana.SEGUNDA, DiaDaSemana.QUARTA, DiaDaSemana.QUINTA, DiaDaSemana.SEXTA), null);
    private Turno turno2 = new Turno(null, null, 5, 50, LocalTime.parse("11:00"),
			EnumSet.of(DiaDaSemana.SEGUNDA, DiaDaSemana.QUARTA, DiaDaSemana.QUINTA, DiaDaSemana.SEXTA), null);
	
//    private List<Turno> turnos = new ArrayList<>(turno1 ,turno2);
    
    private Domain<List<HorarioVariable>> generateDomain(TurmaVariable t, List<HorarioVariable> allHorarios) {
    	Integer cargaHoraria = t.getDisciplina().getDisciplina().getCargaHoraria();
    	List<List<HorarioVariable>> ls = Generator.combination(allHorarios)
    	 	.simple(cargaHoraria).stream().collect(Collectors.toList());
    	return new Domain<List<HorarioVariable>>(ls);
    }
    
    private List<HorarioVariable> range(Turno turnoLocal) {
		/* Quero gerar
 		 * [HorarioVariable(time=1, dia=SEGUNDA), HorarioVariable(time=1, dia=QUARTA), HorarioVariable(time=1, dia=QUINTA), HorarioVariable(time=1, dia=SEXTA), 
 		 *  HorarioVariable(time=2, dia=SEGUNDA), HorarioVariable(time=2, dia=QUARTA), HorarioVariable(time=2, dia=QUINTA), HorarioVariable(time=2, dia=SEXTA), 
 		 *  HorarioVariable(time=3, dia=SEGUNDA), HorarioVariable(time=3, dia=QUARTA), HorarioVariable(time=3, dia=QUINTA), HorarioVariable(time=3, dia=SEXTA), 
 		 *  HorarioVariable(time=4, dia=SEGUNDA), HorarioVariable(time=4, dia=QUARTA), HorarioVariable(time=4, dia=QUINTA), HorarioVariable(time=4, dia=SEXTA), 
 		 *  HorarioVariable(time=5, dia=SEGUNDA), HorarioVariable(time=5, dia=QUARTA), HorarioVariable(time=5, dia=QUINTA), HorarioVariable(time=5, dia=SEXTA)]
		 */
		List<Integer> range = IntStream.rangeClosed(1, turnoLocal.getQtdAulasDia()).boxed().collect(Collectors.toList());
		List<Integer> dias = turnoLocal.getDiasDaSemana().stream().map(x -> x.getId()).collect(Collectors.toList());
		
		return Generator.cartesianProduct(range, dias).stream().map(x -> {
			DiaDaSemana d = DiaDaSemana.toEnum(x.get(1));
			return new HorarioVariable(x.get(0) + "," + d.getValor(), d);
		}).collect(Collectors.toList());
	 
	}
    public HorarioToTurnoCSP(List<TurmaVariable> turmas) throws Exception {
        super(turmas); 
        //criação do domínio
        Domain<HorarioVariable> horario = new Domain<>();
         
        // somente com um turno  TARDE
        int aulas = 5;
        int dias = 4;
        
        List<HorarioVariable> allHorarios = range(turno1);
       
       
        for(TurmaVariable t: getVariables()) {
        	setDomain(t, generateDomain(t, allHorarios));
        }
        
        for(TurmaVariable t : getVariables()) {
        	addConstraint(new AulasConsecutivasConstraint<TurmaVariable, List<HorarioVariable>>(t));
//        	addConstraint(new AulasParalelasConstraint<TurmaVariable, List<HorarioVariable>>(t));
//        	addConstraint(new ExatamenteMetadeDaCargaConstraint<TurmaVariable, List<HorarioVariable>>(t));
        }
    }  
}

