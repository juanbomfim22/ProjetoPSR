package br.ufs.dcomp.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.dto.DocenteDTO;
import br.ufs.dcomp.projetopsr.security.UserPrincipal;
import br.ufs.dcomp.projetopsr.services.DocenteService;
import br.ufs.dcomp.projetopsr.services.TurnoService;

@RestController
@RequestMapping(value="/docentes")
public class DocenteResource {
	 
	@Autowired
	private DocenteService service;
	
	@Autowired
	private TurnoService turnoService;
	
	
	@GetMapping
	public ResponseEntity<Page<DocenteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Docente> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<DocenteDTO> listDTO = list.map(x -> new DocenteDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Docente obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody @Validated DocenteDTO d,
			@RequestParam(name="turnoId") Integer turnoId, 
			@RequestParam(value = "disciplinaIds", required=false) String[] param,
			Authentication auth) {
		Turno t = null;
		if(auth != null) {
			t = turnoService.isFromUser(turnoId, (UserPrincipal) auth.getPrincipal());
		}
		Docente obj = service.inserir(service.fromDTO(d, t));
		return ResponseEntity.ok(obj);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody @Validated DocenteDTO d, 
			@PathVariable Integer id, @RequestParam(value = "disciplinaIds", required=false) String[] params) {
		service.updateBulk(params, id); 
		return ResponseEntity.noContent().build();
	}
	
//	@PutMapping("/{id}/disciplinas")
//	public ResponseEntity<Void> updateDisciplinas(@PathVariable Integer id, 
//			@RequestParam(value = "disciplinaIds") String[] params) {
//		service.updateBulk(params, id); 
//		return ResponseEntity.noContent().build();
//	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
