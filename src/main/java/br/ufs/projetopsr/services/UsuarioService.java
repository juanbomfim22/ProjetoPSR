package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Usuario;
import br.ufs.projetopsr.dto.UsuarioDTO;
import br.ufs.projetopsr.repositories.UsuarioRepository;
import br.ufs.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario buscar(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName() ));
	}
	
	public Usuario inserir(Usuario obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		Usuario usr = new Usuario(null, objDto.getNome(), objDto.getEmail(), pe.encode(objDto.getSenha()));
		return usr;
	}
}
