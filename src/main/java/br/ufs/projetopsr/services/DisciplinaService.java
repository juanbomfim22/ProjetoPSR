package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.dto.DisciplinaDTO;
import br.ufs.projetopsr.repositories.DisciplinaRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repo;
	 
	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir a disciplina");
		}
	}
	
	public Page<Disciplina> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
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
	
	public Disciplina update(Disciplina obj, Integer id) {
		buscar(id);
		Disciplina newObj = buscar(id);
		
		newObj.setNome(obj.getNome());  
		newObj.setCargaHoraria(obj.getCargaHoraria());
		newObj.setCodigo(obj.getCodigo());
	
		return repo.save(newObj);
	}
	
	public Disciplina fromDTO(DisciplinaDTO dto, Integer id) {
		Disciplina ds = buscar(id); 
		Disciplina d = new Disciplina(id, dto.getNome(), dto.getCodigo(), dto.getCargaHoraria(), ds.getDocente());
		return d; 
	}
	
}
