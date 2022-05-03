package br.ufs.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.services.DisciplinaService;

@RestController
@RequestMapping(value="/disciplinas")
public class DisciplinaResource {
	 
	@Autowired
	private DisciplinaService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Disciplina obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
}
