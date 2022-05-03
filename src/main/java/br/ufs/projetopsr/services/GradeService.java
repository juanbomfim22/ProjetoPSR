package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Grade;
import br.ufs.projetopsr.repositories.GradeRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class GradeService {
	
	@Autowired
	private GradeRepository repo;
	
	public Grade buscar(Integer id) {
		Optional<Grade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Grade.class.getName() ));
	}
}
