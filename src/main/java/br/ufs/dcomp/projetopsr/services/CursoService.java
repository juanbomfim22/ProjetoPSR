package br.ufs.dcomp.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.repositories.CursoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class CursoService {
	
	@Autowired
	private CursoRepository repo;
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir o curso");
		}
	}
	
	public Page<Curso> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Curso buscar(Integer id) {
		Optional<Curso> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName() ));
	}
	
	public Curso inserir(Curso obj) {
		obj.setId(null); 
		obj = repo.save(obj);
		return obj;
	} 
}
