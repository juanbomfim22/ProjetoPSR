package br.ufs.dcomp.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.dcomp.projetopsr.services.MongoService;

@RestController
@RequestMapping("/iclass")
public class MongoResource {
	@Autowired
	private MongoService serv;
	
	@GetMapping("/grade/{id}")
	public ResponseEntity<?> buscarGrade(@PathVariable Integer id){
		return ResponseEntity.ok(serv.buscar("grade", id));
	}	
	
	@GetMapping("/disciplina/{id}")
	public ResponseEntity<?> buscarDisciplina(@PathVariable Integer id){
		return ResponseEntity.ok(serv.buscar("disciplina", id));
	}
	
	@GetMapping("/professor/{id}")
	public ResponseEntity<?> buscarProfessor(@PathVariable Integer id){
		return ResponseEntity.ok(serv.buscar("professor", id));
	}
	
}
