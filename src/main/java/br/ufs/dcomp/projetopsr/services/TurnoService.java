package br.ufs.dcomp.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.dto.TurnoDTO;
import br.ufs.dcomp.projetopsr.repositories.GradeRepository;
import br.ufs.dcomp.projetopsr.repositories.TurnoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository repo;

	@Autowired
	private GradeRepository gradeRepo;

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir o turno");
		}
	}
	
	public Page<Turno> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Turno buscar(Integer id) {
		Optional<Turno> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Turno não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Turno inserir(Turno obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	// UPDATE
	public Turno fromDTO(TurnoDTO dto, Integer id) {
//		Turno tmp = buscar(id);
//		Grade gr = gradeRepo.findById(dto.getGradeId())
//				.orElseThrow(() -> new ObjectNotFoundException("Grade não encontrada! Id: " + dto.getGradeId()));
		buscar(id);
		Turno t = new Turno(null, dto.getNome(), null, null, null, null);
		return t;
		
//		return tmp;
	}
	
	// INSERT
	public Turno fromDTO(TurnoDTO dto) {
		Turno t = new Turno(null, dto.getNome(), null, null, null, null);
		return t;
	}
	
	
	public Turno update(Turno obj) {
		Turno newObj = buscar(obj.getId());
		
		newObj.setNome(obj.getNome()); 
//		newObj.setGrade(obj.getGrade());
//		newObj.setHoraInicio(obj.getHoraInicio());
//		newObj.setHoraTermino(obj.getHoraTermino());

		return repo.save(newObj);
	}
}
