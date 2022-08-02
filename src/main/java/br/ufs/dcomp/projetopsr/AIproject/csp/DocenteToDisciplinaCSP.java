package br.ufs.dcomp.projetopsr.AIproject.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import br.ufs.dcomp.projetopsr.AIproject.constraints.docdisc.CreditosLecionadosConstraint;
import br.ufs.dcomp.projetopsr.AIproject.constraints.docdisc.DisciplinaPreferenciaConstraint;
import br.ufs.dcomp.projetopsr.AIproject.constraints.docdisc.DocentesDiferentesConstraint;
import br.ufs.dcomp.projetopsr.AIproject.variables.DisciplinaVariable;
import br.ufs.dcomp.projetopsr.AIproject.variables.DocenteVariable;

public class DocenteToDisciplinaCSP extends CSP<DocenteVariable, DisciplinaVariable>{
    public List<DocenteVariable> docentes = new ArrayList<DocenteVariable>();
    public List<DisciplinaVariable> disciplinas = new ArrayList<DisciplinaVariable>();

    /*
     * Atenção: O domínio deve ser ordenado por quem tem prioridade, isto é, 
     * os professores que disseram as disciplnia de preferencias
     * 
     */
    public DocenteToDisciplinaCSP(List<DocenteVariable> docentes, 
    		List<DisciplinaVariable> disciplinas,
    		List<DisciplinaVariable> disciplinasObrigatorias // passada pela instituicao
    		) throws Exception {
        super();
        if(disciplinasObrigatorias.size() > docentes.size()) {
        	throw new Exception("Não pode ter mais disciplinas obrigatórias que docentes");
        }
        
        this.docentes = docentes;
        this.disciplinas = disciplinas;
        
        //adição das variáveis ao problema
        for(int i = 0; i<docentes.size();i++){
            this.addVariable(docentes.get(i));            
        }
        
        //criação do domínio
        Domain<DisciplinaVariable> discs_oferta = new Domain<DisciplinaVariable>(
        		disciplinasObrigatorias
        );
        
        List<DocenteVariable> docs = getVariables();
        //definição do domínio para cada variável
        for (DocenteVariable var : docs)
            setDomain(var, discs_oferta); 
           
        
        //adicionando constraint de dois professores não podem dar a mesma disciplina 
        //pois uma disciplina só terá uma turma
        // Faz todas as combinaçoes de professores
        for(int i=0; i<docs.size(); i++){
            for(int j=0; j<docs.size(); j++){
                if(!docs.get(i).getName().equals(docs.get(j).getName())){
                    addConstraint(new DocentesDiferentesConstraint<DocenteVariable, DisciplinaVariable>(docs.get(i), docs.get(j)));     
                }
            }
        }
        
        //adição do restante das constraints
        for(int i=0; i< docs.size(); i++){
//            addConstraint(new ConstraintTurno(docs.get(i)));
        	// A constraint deve ser adicionada somente para quem tem 
            if(!docs.get(i).getDocente().getPreferencias().isEmpty()) {            	
            	addConstraint(new DisciplinaPreferenciaConstraint<DocenteVariable, DisciplinaVariable>(docs.get(i)));
            }
            addConstraint(new CreditosLecionadosConstraint<DocenteVariable, DisciplinaVariable>(docs.get(i)));
        } 
        
    }
    
    
    
    
}

