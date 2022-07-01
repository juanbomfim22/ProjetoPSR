package br.ufs.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.domain.Docente;
import br.ufs.projetopsr.services.DocenteService;

@RestController
@RequestMapping(value="/docentes")
public class DocenteResource {
	 
	@Autowired
	private DocenteService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Docente obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Docente d) {
		Docente obj = service.inserir(d);
		return ResponseEntity.ok(obj);
	}
}
