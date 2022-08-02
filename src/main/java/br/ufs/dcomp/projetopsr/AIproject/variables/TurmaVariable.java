package br.ufs.dcomp.projetopsr.AIproject.variables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aima.core.search.csp.Variable;
import lombok.Getter;
import lombok.Setter;

// É o que pode colocar dentro de cada Timebox
// Uma disciplina com os membros (professores)
@Getter
@Setter
public class TurmaVariable extends Variable {
	private Integer quantosHorariosUsa = 0;
	private DisciplinaVariable disciplina;
	private List<DocenteVariable> docentes; // uma lista para permitir ser mais de um prof na turma
	
	public TurmaVariable() {
		super("");
	}
	
	public TurmaVariable(String name) {
		super(name);
		this.docentes = new ArrayList<>();
	}
	
	public TurmaVariable(DisciplinaVariable disciplina, List<DocenteVariable> docentes) {
		this(disciplina.getName() + " " + docentes.get(0).getName());
		this.disciplina = disciplina;
		this.docentes = docentes;
	}

	@Override
	public String toString() {
		if(disciplina == null) {
			return "{}";
		}
		String joined = docentes.stream()
				.map(DocenteVariable::toString).collect(Collectors.joining("e "));
		
		return "[" + joined + ", " + disciplina.getDisciplina().getCodigo() + "]";
	}
	
	 
}
