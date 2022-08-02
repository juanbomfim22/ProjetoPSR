package br.ufs.dcomp.projetopsr.services;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Curso;
import br.ufs.dcomp.projetopsr.domain.Disciplina;
import br.ufs.dcomp.projetopsr.domain.Docente;
import br.ufs.dcomp.projetopsr.domain.Grade;
import br.ufs.dcomp.projetopsr.domain.Instituicao;
import br.ufs.dcomp.projetopsr.domain.Restricao;
import br.ufs.dcomp.projetopsr.domain.Turno;
import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.domain.enums.CursoSigla;
import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;
import br.ufs.dcomp.projetopsr.repositories.CursoRepository;
import br.ufs.dcomp.projetopsr.repositories.DisciplinaRepository;
import br.ufs.dcomp.projetopsr.repositories.DocenteRepository;
import br.ufs.dcomp.projetopsr.repositories.GradeRepository;
import br.ufs.dcomp.projetopsr.repositories.InstituicaoRepository;
import br.ufs.dcomp.projetopsr.repositories.RestricaoRepository;
import br.ufs.dcomp.projetopsr.repositories.TurnoRepository;
import br.ufs.dcomp.projetopsr.repositories.UsuarioRepository;

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
	
	@Autowired 
	private InstituicaoRepository instituicaoRepository;
	

	public void instantiateTestDatabase() throws ParseException {
		
		Usuario u1 = new Usuario(null, "juan", "juanbomfim21@gmail.com", null, null, be.encode("123"), null);
		Usuario u2 = new Usuario(null, "juan", "juan@teste2.com", null, null, be.encode("123"), null);
		Usuario u3 = new Usuario(null, "juan", "juan@teste3.com", null, null, be.encode("123"), null);
		Usuario u4 = new Usuario(null, "lizard", "lizard@example.com", null, null, be.encode("lizard123"), null);

		Instituicao i1 = new Instituicao(null, "Univ", "UFS", "222", u2); 
		Instituicao i2 = new Instituicao(null, "Univ", "IFS", "222", u4); 
		Instituicao i3 = new Instituicao(null, "Univ", "IFS", "222", u1); 
		Instituicao i4 = new Instituicao(null, "Univ", "IFS", "222", u3); 

		Curso c1 = new Curso(null,"Engenharia de Computacao", CursoSigla.EC, 6, i1);
		Curso c2 = new Curso(null,"Engenharia de Comportamento", CursoSigla.EC, 6, i2);

		Turno t1 = new Turno(null, "0 - Manha",4, 50, LocalTime.parse("11:00"), EnumSet.of(DiaDaSemana.TERCA, DiaDaSemana.SEXTA), i1);
		Turno t2 = new Turno(null, "1 - Tarde",4, 50,  LocalTime.parse("11:00"),null, i2);
		Turno t3 = new Turno(null, "2 - Noite", 4, 50,  LocalTime.parse("11:00"),null,i1);
		
		Docente doc1 = new Docente(null, "Carlos");
		Docente doc2 = new Docente(null, "Tarcisio");
		Docente doc3 = new Docente(null, "Leila");
		Docente doc4 = new Docente(null, "Beatriz");
		
		doc1.setTurnos(Arrays.asList(t1, t2));
		doc2.setTurnos(Arrays.asList(t2, t3));
		doc3.setTurnos(Arrays.asList(t2));
		doc4.setTurnos(Arrays.asList(t1));
		
		Restricao r1a = new Restricao(null, doc1);
		Restricao r2a = new Restricao(null, doc2); 

		Disciplina d1 = new Disciplina(null, "0000 - Inteligencia Artificial", "IA", 4, doc1);
		Disciplina d2 = new Disciplina(null, "0001 - Eng Software", "ES", 8, doc2);
		Disciplina d3 = new Disciplina(null, "0002 - Programacao Paralela", "PP", 4, doc2); 

		
		
//		 Deve repetir o saveAll e usar o flush, se nao dá erro Transactional ...
		instituicaoRepository.saveAll(Arrays.asList(i1,i2, i3, i4));
		usuarioRepository.saveAll(Arrays.asList(u1,u2,u3,u4));
		turnoRepository.saveAll(Arrays.asList(t1, t2, t3));
		cursoRepository.saveAll(Arrays.asList(c1,c2));
		restricaoRepository.saveAll(Arrays.asList(r1a, r2a));
		docenteRepository.saveAll(Arrays.asList(doc1, doc2, doc3, doc4));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
		
		usuarioRepository.flush();
		instituicaoRepository.flush();
		turnoRepository.flush();
		cursoRepository.flush();
		docenteRepository.flush();
//		restricaoRepository.flush();
		disciplinaRepository.flush();
		//
		
		c1.getDisciplinasObrigatorias().addAll(Arrays.asList(d1, d2, d3));

		d1.getCursos().addAll(Arrays.asList(c1,c2));
		d2.getCursos().addAll(Arrays.asList(c1));
		d3.getCursos().addAll(Arrays.asList(c1));
		
		doc1.setPreferencias(Arrays.asList(d1));
		doc2.setPreferencias(Arrays.asList(d2, d3));
		
//		r1a.getPreferencias().addAll(Arrays.asList(d2, d3));
		
		Map<DiaDaSemana,String> a = new HashMap<>();
		a.put(DiaDaSemana.SABADO, "1,2,3");
		a.put(DiaDaSemana.DOMINGO, "1,2");
		a.put(DiaDaSemana.SEGUNDA, "6,14,15");

		r1a.setRestricoesDeHorario(a);
				
		
//		doc1.getRestricao().setPreferencias(Arrays.asList(d2, d3));
//		doc2.getRestricao().setRestricoesDeHorario(a);

		cursoRepository.saveAll(Arrays.asList(c1, c2));
		docenteRepository.saveAll(Arrays.asList(doc1, doc2));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
		restricaoRepository.saveAll(Arrays.asList(r1a, r2a));
		 
		Grade g1 = new Grade(null, "Grade UFS EC", new Date(), t1);
		
//		Turno t1 = new Turno(null, "manha", new Date(), new Date(),g1);
//		Turno t2 = new Turno(null, "tarde",new Date(), new Date(),g1);
//		Turno t3 = new Turno(null, "noite",new Date(), new Date(),g1); 
 
		
//		g1.getDocentes().addAll(Arrays.asList(doc1, doc2));
//		g1.getDisciplinas().addAll(Arrays.asList(d1, d2, d3));
//		g1.setUsuario(u1);
//		g1.getTurnos().addAll(Arrays.asList(t1, t2, t3));
		
		// apagar
//		usuarioRepository.saveAll(Arrays.asList(u1));
//		gradeRepository.saveAll(Arrays.asList(g1));
//		turnoRepository.saveAll(Arrays.asList(t1, t2, t3));
//		
//		turnoRepository.flush();
//		usuarioRepository.flush();
//		gradeRepository.flush();
//		//
//
//		turnoRepository.saveAll(Arrays.asList(t1, t2, t3));
//		gradeRepository.saveAll(Arrays.asList(g1));
//		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));
	}
}
