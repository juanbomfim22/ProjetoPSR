package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.repositories.DisciplinaRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repo;
	
	public Disciplina buscar(Integer id) {
		Optional<Disciplina> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Disciplina.class.getName() ));
	}
	
	public Disciplina inserir(Disciplina obj) {
		obj.setId(null); 
		obj = repo.save(obj);
		return obj;
	}
	
}
