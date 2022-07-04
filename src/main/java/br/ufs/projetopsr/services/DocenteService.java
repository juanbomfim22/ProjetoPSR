package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Docente;
import br.ufs.projetopsr.repositories.DocenteRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class DocenteService {
	
	@Autowired
	private DocenteRepository repo;

	public Page<Docente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Docente buscar(Integer id) {
		Optional<Docente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Docente.class.getName() ));
	}
	
	public Docente inserir(Docente obj) {
		obj.setId(null); 
		obj = repo.save(obj);
		return obj;
	}
}
