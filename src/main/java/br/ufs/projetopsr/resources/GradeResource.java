package br.ufs.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.domain.Grade;
import br.ufs.projetopsr.services.GradeService;

@RestController
@RequestMapping(value="/grades")
public class GradeResource {
	 
	@Autowired
	private GradeService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Grade obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
}
