package br.ufs.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.dto.DisciplinaDTO;
import br.ufs.projetopsr.services.DisciplinaService;

@RestController
@RequestMapping(value="/disciplinas")
public class DisciplinaResource {
	 
	@Autowired
	private DisciplinaService service;
	
	@GetMapping
	public ResponseEntity<Page<DisciplinaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Disciplina> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<DisciplinaDTO> listDTO = list.map(x -> new DisciplinaDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Disciplina obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Disciplina d) {
		Disciplina obj = service.inserir(d);
		return ResponseEntity.ok(obj);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody @Validated DisciplinaDTO dto, @PathVariable Integer id){
		Disciplina obj = service.fromDTO(dto, id);
		obj = service.update(obj, id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
