package br.ufs.dcomp.projetopsr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Disciplina;
import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Restricao;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import br.ufs.dcomp.projetopsr.dto.DocenteDTO;
import br.ufs.dcomp.projetopsr.repositories.DisciplinaRepository;
import br.ufs.dcomp.projetopsr.repositories.DocenteRepository;
import br.ufs.dcomp.projetopsr.repositories.RestricaoRepository;
import br.ufs.dcomp.projetopsr.repositories.TurnoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class DocenteService {
	
	@Autowired
	private DocenteRepository repo;

	@Autowired
	private DisciplinaRepository disciplinaRepo;
	
	@Autowired
	private RestricaoRepository restricaoRepo;
	
	@Autowired
	private TurnoRepository turnoRepo;
	 
	
	private void myForEach(List<Disciplina> ls, Docente t, boolean clear) {
		ls.forEach(x -> {
			if (clear) {
				x.setDocente(null);
			} else {
				x.setDocente(t);
			}
			disciplinaRepo.save(x);
		});
	}
	
	public Docente update(Docente obj, Integer id) {
		Docente newObj = buscar(id);
		Map<DiaDaSemana, String> res = obj.getRestricao().getRestricoesDeHorario();
		for(String value : res.values()) { 
			List<Integer> items = Stream.of(value.split(","))
				     .map(x -> {
				    	 Integer k;
				    	 try {
				    		 k = Integer.parseInt(x.trim());
				    	 } catch(Exception e){
				    		 throw new ObjectNotFoundException(
				    					"Valor não é inteiro: " + x  );
				    	 }
				    	 return k;
				     })
				     .collect(Collectors.toList());
		}
		
		newObj.setNome(obj.getNome()); 
		newObj.setDisciplinas(obj.getDisciplinas());
		newObj.setNome(obj.getNome());
		newObj.setTurno(obj.getTurno());

//	 	newObj.getRestricao().setRestricoesDeHorario(obj.getRestricao().getRestricoesDeHorario());
 
//		restricaoRepo.save(r);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		Docente d = buscar(id);
		myForEach(d.getDisciplinas(), null ,true);
		try {
			repo.deleteById(id);			
		} catch(DataIntegrityViolationException e) {
			System.err.println(e);
			throw new DataIntegrityViolationException("Não é possível excluir o docente");
		}
	}
	
	public Page<Docente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Docente buscar(Integer id) {
		Optional<Docente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Docente.class.getName() ));
	}
	
	public void updateBulk(String[] disciplinaIds, Integer docId) {
		List<Disciplina> discs = new ArrayList<Disciplina>();
		List<Integer> repeated = new ArrayList<Integer>();
		for (String id : disciplinaIds) {
			Integer x;
			try {
				x = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				throw e;
			}

			if(!repeated.contains(x)) {
				Disciplina disc = disciplinaRepo.findById(x).orElseThrow(() -> new ObjectNotFoundException(
						"Disicplina não encontrado! Id: " + id + ", Tipo: " + Disciplina.class.getName()));
				discs.add(disc);
			}
			
			repeated.add(x);
		}
		Docente d = buscar(docId);
		myForEach(d.getDisciplinas(), null, true);
		myForEach(discs, d, false);
	}
	
	public Docente fromDTO(DocenteDTO d, Turno t) {
		
		
		Docente doc = new Docente();
				
		doc.setRestricao(d.getRestricao());

//		d.getRestricao().setRestricoesDeHorario(d.getRestricao().getRestricoesDeHorario());
		doc.setMatricula(d.getMatricula());
		doc.setNome(d.getNome());
		doc.setTurno(t);
		return doc;
	}
	
	public Docente fromDTO (DocenteDTO d, Integer id) {
		Docente doc = buscar(id);
		doc.setNome(d.getNome());
		doc.getRestricao().setRestricoesDeHorario(d.getRestricao().getRestricoesDeHorario());
		return doc;
	}
	
	public Docente inserir(Docente obj) {
		obj.setId(null); 
		Restricao r = obj.getRestricao();
		r.setDocente(obj);
		restricaoRepo.save(r);
		obj = repo.save(obj);
		return obj;
	}
}
