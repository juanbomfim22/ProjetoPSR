package br.ufs.projetopsr.services;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Curso;
import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.domain.Docente;
import br.ufs.projetopsr.domain.Grade;
import br.ufs.projetopsr.domain.Turno;
import br.ufs.projetopsr.domain.Usuario;
import br.ufs.projetopsr.domain.enums.CursoSigla;
import br.ufs.projetopsr.domain.enums.TurnoDia;
import br.ufs.projetopsr.repositories.CursoRepository;
import br.ufs.projetopsr.repositories.DisciplinaRepository;
import br.ufs.projetopsr.repositories.DocenteRepository;
import br.ufs.projetopsr.repositories.GradeRepository;
import br.ufs.projetopsr.repositories.RestricaoRepository;
import br.ufs.projetopsr.repositories.TurnoRepository;
import br.ufs.projetopsr.repositories.UsuarioRepository;

@Service
public class DBService {
	@Autowired
	private BCryptPasswordEncoder be;
	
	@Autowired 
	private CursoRepository cursoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private DocenteRepository docenteRepository;
	
	@Autowired
	private RestricaoRepository restricaoRepository;

	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	private TurnoRepository turnoRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		Curso c1 = new Curso(null,"Engenharia de Computacao", CursoSigla.EC, 6, "UFS");
		Docente doc1 = new Docente(null, "Carlos", TurnoDia.toEnum(2));
		Docente doc2 = new Docente(null, "Tarcisio", TurnoDia.toEnum(3));
		Docente doc3 = new Docente(null, "Leila", TurnoDia.toEnum(3));
		Docente doc4 = new Docente(null, "Beatriz", TurnoDia.toEnum(1));

		
		Disciplina d1 = new Disciplina(null, "Inteligencia Artificial", "IA", 4, doc1);
		Disciplina d2 = new Disciplina(null, "Eng Software", "ES", 8, doc2);
		Disciplina d3 = new Disciplina(null, "Programacao Paralela", "PP", 4, doc2); 
		
		
		// Deve repetir o saveAll e usar o flush, se nao dá erro Transactional ...
		cursoRepository.saveAll(Arrays.asList(c1));
		docenteRepository.saveAll(Arrays.asList(doc1, doc2, doc3, doc4));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
//		restricaoRepository.saveAll(Arrays.asList(r1a, r1b, r2));
		
		cursoRepository.flush();
		docenteRepository.flush();
		disciplinaRepository.flush();
		restricaoRepository.flush();
		//
		
		c1.getDisciplinas().addAll(Arrays.asList(d1, d2, d3));
		
		d1.getCursos().addAll(Arrays.asList(c1));
		d2.getCursos().addAll(Arrays.asList(c1));
		d3.getCursos().addAll(Arrays.asList(c1));

		
		doc1.setDisciplinas(Arrays.asList(d1));
		doc2.setDisciplinas(Arrays.asList(d2, d3));
				
//		doc1.getRestricoes().addAll(Arrays.asList(r1a, r1b));
//		doc2.getRestricoes().addAll(Arrays.asList(r2));

		cursoRepository.saveAll(Arrays.asList(c1));
		docenteRepository.saveAll(Arrays.asList(doc1, doc2));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
//		restricaoRepository.saveAll(Arrays.asList(r1a, r1b, r2));
		 
		Usuario u1 = new Usuario(null, "juan", "juanbomfim21@gmail.com", null, null, be.encode("123"));
		Usuario u2 = new Usuario(null, "juan", "juan@teste2.com", null, null, be.encode("123"));
		Usuario u3 = new Usuario(null, "juan", "juan@teste3.com", null, null, be.encode("123"));


		Grade g1 = new Grade(null, "Grade UFS EC", new Date());
		
//		Turno t1 = new Turno(null, "manha", new Date(), new Date(),g1);
//		Turno t2 = new Turno(null, "tarde",new Date(), new Date(),g1);
//		Turno t3 = new Turno(null, "noite",new Date(), new Date(),g1);
		
		Turno t1 = new Turno(null, "manha", "11", "12");
		Turno t2 = new Turno(null, "tarde", "11", "12");
		Turno t3 = new Turno(null, "noite", "11", "12");
 
		
		g1.getDocentes().addAll(Arrays.asList(doc1, doc2));
		g1.getDisciplinas().addAll(Arrays.asList(d1, d2, d3));
		g1.setUsuario(u1);
//		g1.getTurnos().addAll(Arrays.asList(t1, t2, t3));
		
		// apagar
		usuarioRepository.saveAll(Arrays.asList(u1));
		gradeRepository.saveAll(Arrays.asList(g1));
		turnoRepository.saveAll(Arrays.asList(t1, t2, t3));
		
		turnoRepository.flush();
		usuarioRepository.flush();
		gradeRepository.flush();
		//
		

		turnoRepository.saveAll(Arrays.asList(t1, t2, t3));
		gradeRepository.saveAll(Arrays.asList(g1));
		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));
	}
}
