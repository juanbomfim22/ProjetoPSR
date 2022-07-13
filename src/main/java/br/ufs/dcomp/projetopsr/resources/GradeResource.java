package br.ufs.dcomp.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.dcomp.projetopsr.domain.Grade;
import br.ufs.dcomp.projetopsr.dto.GradeDTO;
import br.ufs.dcomp.projetopsr.services.GradeService;

@RestController
@RequestMapping(value="/grades")
public class GradeResource {
	 
	@Autowired
	private GradeService service;
	
	@GetMapping
	public ResponseEntity<Page<GradeDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Grade> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<GradeDTO> listDTO = list.map(x -> new GradeDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Grade obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Grade d) {
		Grade obj = service.inserir(d);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
