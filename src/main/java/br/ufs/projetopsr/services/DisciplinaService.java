package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.dto.DisciplinaDTO;
import br.ufs.projetopsr.repositories.DisciplinaRepository;
import br.ufs.projetopsr.repositories.UsuarioRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class DisciplinaService {
	
	@Autowired
	private DisciplinaRepository repo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
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
	
	public Disciplina update(Disciplina obj) {
		Disciplina newObj = buscar(obj.getId());
		
		newObj.setNome(obj.getNome()); 
		newObj.setCargaHoraria(obj.getCargaHoraria());
		newObj.setCodigo(obj.getCodigo());
		newObj.setCursos(obj.getCursos());
		newObj.setDocente(obj.getDocente());
		newObj.setGrades(obj.getGrades());
		

		return repo.save(newObj);
	}
	
	public Disciplina fromDTO(DisciplinaDTO dto, Integer id) {
		Disciplina tmp = buscar(id); // Existe?
		Disciplina d = new Disciplina(tmp.getId() , dto.getNome(), dto.getCodigo(), dto.getCargaHoraria(), tmp.getDocente());
		return d; 
	}
	
}
