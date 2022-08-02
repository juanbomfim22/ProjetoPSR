package br.ufs.dcomp.projetopsr.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.dcomp.projetopsr.domain.Instituicao;
import br.ufs.dcomp.projetopsr.security.UserPrincipal;
import br.ufs.dcomp.projetopsr.services.InstituicaoService;

@RestController
@RequestMapping(value="/instituicoes")
public class InstituicaoResource {
	 
	@Autowired
	private InstituicaoService service;
	
	@GetMapping
	public ResponseEntity<Page<Instituicao>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Instituicao> list = service.findPage(page, linesPerPage, direction, orderBy);
		return ResponseEntity.ok().body(list);
//		Page<Instituicao> listDTO = list.map(x -> new CursoDTO(x));
//		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Instituicao obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize(value = "hasRole('CLIENTE') && #id == authentication.principal.id")
	public ResponseEntity<?> update(@RequestBody Instituicao i, @PathVariable Integer id) {
		service.update(i, id);
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping
	@PreAuthorize(value = "hasRole('CLIENTE')")
	public ResponseEntity<?> inserir(@RequestBody Instituicao i, Authentication auth) {
		Instituicao obj = service.inserir(i, ((UserPrincipal) auth.getPrincipal()).getId());
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
