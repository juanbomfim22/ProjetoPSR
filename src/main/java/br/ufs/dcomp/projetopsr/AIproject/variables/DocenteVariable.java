package br.ufs.dcomp.projetopsr.AIproject.variables;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.projetopsr.domain.Docente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocenteVariable extends Variable {
	private Docente docente; 
	private Integer creditosAtuais = 0;
	private List<DisciplinaVariable> lecionadas = new ArrayList<>();

	public DocenteVariable(Docente docente) {
		super(docente.getNome());
		this.docente = docente;
	}
 
}