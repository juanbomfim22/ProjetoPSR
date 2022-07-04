package br.ufs.projetopsr.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufs.projetopsr.domain.Turno;
import br.ufs.projetopsr.dto.TurnoDTO;
import br.ufs.projetopsr.services.TurnoService;

@RestController
@RequestMapping(value="/turnos")
public class TurnoResource {
	 
	
	@Autowired
	private TurnoService service;
	
	
	@GetMapping
	public ResponseEntity<Page<TurnoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Turno> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<TurnoDTO> listDTO = list.map(x -> new TurnoDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Turno obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@RequestBody @Validated TurnoDTO obj){
		Turno t = service.fromDTO(obj);
		t = service.inserir(t);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(t.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Void> update(@RequestBody @Validated TurnoDTO objDto, @PathVariable Integer id) {
		Turno obj = service.fromDTO(objDto, id);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	
	
}
