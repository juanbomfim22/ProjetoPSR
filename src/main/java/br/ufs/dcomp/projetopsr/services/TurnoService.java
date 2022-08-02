package br.ufs.dcomp.projetopsr.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Instituicao;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.dto.TurnoDTO;
import br.ufs.dcomp.projetopsr.repositories.DisciplinaRepository;
import br.ufs.dcomp.projetopsr.repositories.DocenteRepository;
import br.ufs.dcomp.projetopsr.repositories.GradeRepository;
import br.ufs.dcomp.projetopsr.repositories.InstituicaoRepository;
import br.ufs.dcomp.projetopsr.repositories.TurnoRepository;
import br.ufs.dcomp.projetopsr.security.UserPrincipal;
import br.ufs.dcomp.projetopsr.services.exceptions.AuthorizationException;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository repo;

	@Autowired
	private GradeRepository gradeRepo;

	@Autowired
	private DocenteRepository docenteRepo;
	
	@Autowired
	private DisciplinaRepository disciplinaRepo;

	@Autowired
	private InstituicaoRepository instituicaoRepo;
	
	public void delete(Integer id) {
		Turno t = buscar(id);
		myForEach(t.getDocentes(), null, true);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir o turno");
		}
	}

	public Page<Turno> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Turno buscar(Integer id) {
		Optional<Turno> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Turno não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Turno inserir(Turno obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	private void myForEach(List<Docente> ls, Turno t, boolean clear) {
		ls.forEach(x -> {
			if (clear) {
				x.setTurnos(null);
			} else {
				x.setTurnos(Arrays.asList(t));
			}
			docenteRepo.save(x);
		});
	}
	
	public Turno inserirBulk(String[] docenteIds, Turno t) {
		t = inserir(t);
		myForEach(eachDoc(docenteIds), t, false);
		return t;
	}

	public List<Docente> eachDoc(String[] docenteIds) {
		
		List<Docente> docs = new ArrayList<Docente>();
		List<Integer> repeated = new ArrayList<Integer>();
		
		if(docenteIds == null) return docs;
		
		for (String id : docenteIds) {
			Integer x;
			try {
				x = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				throw e;
			}

			if (!repeated.contains(x)) {
				Docente doc = docenteRepo.findById(x).orElseThrow(() -> new ObjectNotFoundException(
						"Docente não encontrado! Id: " + id + ", Tipo: " + Docente.class.getName()));
				docs.add(doc);
			}

			repeated.add(x);
		}
		return docs;
	}
	public void updateBulk(String[] docenteIds, Turno obj, Integer turnoId) {
		
		Turno t = update(obj, turnoId); 
		myForEach(t.getDocentes(), null, true);
		myForEach(eachDoc(docenteIds), t, false);
	}

	public Turno fromDTOInst(TurnoDTO dto, Integer instId) {
		Instituicao i = instituicaoRepo.findById(instId).orElseThrow(() -> new ObjectNotFoundException(
				"Instituicao não encontrada! Id: " + instId + ", Tipo: " + Instituicao.class.getName()));
		Turno t = fromDTO(dto);
		t.setInstituicao(i);
		return t;
	}
	
	// UPDATE
	public Turno fromDTO(TurnoDTO dto, Integer id) {
		buscar(id);
		return fromDTO(dto);
	}

	
	
	// INSERT
	public Turno fromDTO(TurnoDTO dto) {
		Turno t = new Turno(null, dto.getNome(), dto.getQtdAulasDia(), dto.getDuracaoAula(), dto.getHoraInicio(),
				dto.getDiasDaSemana(), null);
		return t;
	}

	public Turno update(Turno obj, Integer id) {
//		buscar(id);
		Turno newObj = buscar(id);

		newObj.setNome(obj.getNome());
		newObj.setDiasDaSemana(obj.getDiasDaSemana());
		newObj.setQtdAulasDia(obj.getQtdAulasDia());
		newObj.setDuracaoAula(obj.getDuracaoAula());
		newObj.setHoraInicio(obj.getHoraInicio());
		return repo.save(newObj);

//		Turno res = new Turno(obj.getId(), o.getNome(), o.getQtdAulasDia(), o.getDuracaoAula(), o.getHoraInicio(), 
//				EnumSet.copyOf(o.getDiasDaSemana()), 
//				obj.getInstituicao()); // atualiza tudo menos a instituicao
////		
//		return repo.save(res);
	}
	
	public Turno isFromUser(Integer turnoId, UserPrincipal user) {
		Optional<Turno> t = repo.findTurnoByUsuarioId(turnoId, user.getId());
		return t.orElseThrow(() -> new AuthorizationException(
				"Este ID não pertence a um turno da sua conta!"));
	}
}
