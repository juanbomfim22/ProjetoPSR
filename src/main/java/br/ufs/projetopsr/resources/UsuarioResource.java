package br.ufs.projetopsr.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufs.projetopsr.domain.Usuario;
import br.ufs.projetopsr.dto.UsuarioDTO;
import br.ufs.projetopsr.services.UsuarioService;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {
	 
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
//	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Page<UsuarioDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		Page<Usuario> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<UsuarioDTO> listDTO = list.map(x -> new UsuarioDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CLIENTE') && #id == authentication.principal.id")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Usuario obj = service.buscar(id);
		return ResponseEntity.ok(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> inserir(@RequestBody @Validated UsuarioDTO obj){
		Usuario user = service.fromDTO(obj);
		user = service.inserir(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// SE O ENDPOINT NAO TIVER CARREGANDO, OLHE NO SPRING SECURITY...
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
