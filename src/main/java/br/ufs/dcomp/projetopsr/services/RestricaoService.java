package br.ufs.dcomp.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.domain.Restricao;
import br.ufs.dcomp.projetopsr.repositories.RestricaoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class RestricaoService {
	
	@Autowired
	private RestricaoRepository repo;
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir o curso");
		}
	}
	
	public Page<Restricao> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Restricao buscar(Integer id) {
		Optional<Restricao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName() ));
	}
	
	public Restricao inserir(Restricao obj) {
		obj.setId(null); 
		obj = repo.save(obj);
		return obj;
	} 
}
