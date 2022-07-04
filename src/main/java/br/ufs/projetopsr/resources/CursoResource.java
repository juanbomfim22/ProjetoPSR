package br.ufs.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.domain.Curso;
import br.ufs.projetopsr.dto.CursoDTO;
import br.ufs.projetopsr.services.CursoService;

@RestController
@RequestMapping(value="/cursos")
public class CursoResource {
	 
	@Autowired
	private CursoService service;
	
	@GetMapping
	public ResponseEntity<Page<CursoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Curso> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<CursoDTO> listDTO = list.map(x -> new CursoDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Curso obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Curso curso) {
		Curso obj = service.inserir(curso);
		return ResponseEntity.ok(obj);
	}
	
	
}
