package br.ufs.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir o usuário");
		}
	}

	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Usuario buscar(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Usuario inserir(Usuario obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	public Usuario fromDTO(UsuarioDTO objDto) {
		Usuario usr = new Usuario(null, objDto.getNome(), objDto.getEmail(), null, null, pe.encode(objDto.getSenha()));
		return usr;
	}
}
