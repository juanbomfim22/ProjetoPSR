package br.ufs.dcomp.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.domain.Instituicao;
import br.ufs.dcomp.projetopsr.repositories.InstituicaoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class InstituicaoService {
	
	@Autowired
	private InstituicaoRepository repo;
	
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir o curso");
		}
	}
	
	public Page<Instituicao> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Instituicao buscar(Integer id) {
		Optional<Instituicao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName() ));
	}
	
	public Instituicao inserir(Instituicao obj) {
		obj.setId(null); 
		obj = repo.save(obj);
		return obj;
	} 
}
