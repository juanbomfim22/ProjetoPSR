package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Docente;
import br.ufs.projetopsr.repositories.DocenteRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class DocenteService {
	
	@Autowired
	private DocenteRepository repo;
	
	public Docente buscar(Integer id) {
		Optional<Docente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Docente.class.getName() ));
	}
}
