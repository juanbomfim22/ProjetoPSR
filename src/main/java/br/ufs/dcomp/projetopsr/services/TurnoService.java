package br.ufs.dcomp.projetopsr.services;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.dto.TurnoDTO;
import br.ufs.dcomp.projetopsr.repositories.DocenteRepository;
import br.ufs.dcomp.projetopsr.repositories.GradeRepository;
import br.ufs.dcomp.projetopsr.repositories.TurnoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository repo;

	@Autowired
	private GradeRepository gradeRepo;

	@Autowired
	private DocenteRepository docenteRepo;

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
				x.setTurno(null);
			} else {
				x.setTurno(t);
			}
			docenteRepo.save(x);
		});
	}

	public void updateBulk(String[] docenteIds, Integer turnoId) {
		List<Docente> docs = new ArrayList<Docente>();
		List<Integer> repeated = new ArrayList<Integer>();
		for (String id : docenteIds) {
			Integer x;
			try {
				x = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				throw e;
			}

			if (!repeated.contains(x)) {
				Docente doc = docenteRepo.findById(x).orElseThrow(() -> new ObjectNotFoundException(
						"Turno não encontrado! Id: " + id + ", Tipo: " + Turno.class.getName()));
				docs.add(doc);
			}

			repeated.add(x);
		}
		Turno t = buscar(turnoId);
		myForEach(t.getDocentes(), null, true);
		myForEach(docs, t, false);
	}

	// UPDATE
	public Turno fromDTO(TurnoDTO dto, Integer id) {
		buscar(id);
		return fromDTO(dto);

//		return tmp;
	}

	// INSERT
	public Turno fromDTO(TurnoDTO dto) {
		Turno t = new Turno(null, dto.getNome(), dto.getQtdAulasDia(), dto.getDuracaoAula(), dto.getHoraInicio(),
				EnumSet.copyOf(dto.getDiasDaSemana()), null);
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
}
