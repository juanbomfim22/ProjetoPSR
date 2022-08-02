package br.ufs.dcomp.projetopsr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.domain.Instituicao;
import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.repositories.InstituicaoRepository;
import br.ufs.dcomp.projetopsr.repositories.UsuarioRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepository repo;

	@Autowired
	private UsuarioRepository usuarioRepo;

	public void delete(Integer id) {
		Instituicao i = buscar(id);
		// Para que o usuario nao seja deletado no cascade (inclusive dá erro 500)
		Usuario u = i.getUsuario();
		u.setInstituicao(null);
		usuarioRepo.save(u);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir a instituição");
		}
	}

	public Page<Instituicao> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Instituicao buscar(Integer id) {
		Optional<Instituicao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName()));
	}

	public void update(Instituicao i, Integer id) {
	 	Instituicao oldI = buscar(id);
		oldI.setCnpj(i.getCnpj());
		oldI.setCursos(i.getCursos());
		oldI.setNome(i.getNome());
		oldI.setSigla(i.getSigla());
		oldI.setTurnos(i.getTurnos());
		repo.save(oldI);
	}
	
	public Instituicao inserir(Instituicao obj, Integer userId) {

		Usuario user = usuarioRepo.findById(userId).orElseThrow(() -> new ObjectNotFoundException(
				"Usuario não encontrado! Id: " + userId + ", Tipo: " + Usuario.class.getName()));
		if (user.getInstituicao() == null) {
			obj.setId(null);
			obj.setUsuario(user);
			obj = repo.save(obj);
		} else {
			Optional.empty().orElseThrow( () -> new ObjectNotFoundException(
				"A Instituicao para esse Usuário já existe, tente atualiza-la com PUT!"));
		}
		return obj;
	}
}
