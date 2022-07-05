package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Grade;
import br.ufs.projetopsr.repositories.GradeRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class GradeService {
	
	@Autowired
	private GradeRepository repo;
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir a grade");
		}
	}
	
	public Page<Grade> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Grade buscar(Integer id) {
		Optional<Grade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Grade.class.getName() ));
	}
	
	public Grade inserir(Grade obj) {
		obj.setId(null); 
		obj = repo.save(obj);
		return obj;
	}
}
