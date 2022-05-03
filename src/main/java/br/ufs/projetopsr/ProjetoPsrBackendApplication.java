package br.ufs.projetopsr;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.ufs.projetopsr.domain.Curso;
import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.domain.Docente;
import br.ufs.projetopsr.domain.Grade;
import br.ufs.projetopsr.domain.Restricao;
import br.ufs.projetopsr.domain.RestricaoIndisponibilidade;
import br.ufs.projetopsr.domain.RestricaoPreferencia;
import br.ufs.projetopsr.domain.Usuario;
import br.ufs.projetopsr.domain.enums.CursoSigla;
import br.ufs.projetopsr.domain.enums.DiaDaSemana;
import br.ufs.projetopsr.domain.enums.Turno;
import br.ufs.projetopsr.repositories.CursoRepository;
import br.ufs.projetopsr.repositories.DisciplinaRepository;
import br.ufs.projetopsr.repositories.DocenteRepository;
import br.ufs.projetopsr.repositories.GradeRepository;
import br.ufs.projetopsr.repositories.RestricaoRepository;
import br.ufs.projetopsr.repositories.UsuarioRepository;

@SpringBootApplication
public class ProjetoPsrBackendApplication implements CommandLineRunner {

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
	
	//@Autowired
	
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoPsrBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Curso c1 = new Curso(null,"Engenharia de Computacao", CursoSigla.EC, 6, "UFS");
		
		Docente doc1 = new Docente(null, "Carlos Alberto", Turno.toEnum(2));
		Docente doc2 = new Docente(null, "Leonardo", Turno.toEnum(3));
		
		Disciplina d1 = new Disciplina(null, "Inteligencia Artificial", "IA", 4, doc1);
		Disciplina d2 = new Disciplina(null, "Eng Software", "ES", 8, doc2);
		Disciplina d3 = new Disciplina(null, "Programacao Paralela", "PP", 4, doc2); 
		
		Restricao r1a = new RestricaoIndisponibilidade(null, "Restr 1",DiaDaSemana.SEGUNDA, doc1, "Não pode dar aula de noite");
		Restricao r1b = new RestricaoPreferencia(null, "Terça pode", DiaDaSemana.TERCA, doc1, 5);
		Restricao r2 = new RestricaoIndisponibilidade(null, "Restr 3", DiaDaSemana.QUARTA, doc2, "Não pode dar aula de manha");

		// Deve repetir o saveAll e usar o flush, se nao dá erro Transactional ...
		cursoRepository.saveAll(Arrays.asList(c1));
		docenteRepository.saveAll(Arrays.asList(doc1, doc2));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
		restricaoRepository.saveAll(Arrays.asList(r1a, r1b, r2));
		
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
				
		doc1.getRestricoes().addAll(Arrays.asList(r1a, r1b));
		doc2.getRestricoes().addAll(Arrays.asList(r2));

		cursoRepository.saveAll(Arrays.asList(c1));
		docenteRepository.saveAll(Arrays.asList(doc1, doc2));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
		restricaoRepository.saveAll(Arrays.asList(r1a, r1b, r2));
		 
		Usuario u1 = new Usuario(null, "juan", "juan@teste.com");
		
		Grade g1 = new Grade(null, "Grade UFS EC", new Date());
		g1.getDocentes().addAll(Arrays.asList(doc1, doc2));
		g1.getDisciplinas().addAll(Arrays.asList(d1, d2, d3));
		g1.setUsuario(u1);
		
		// apagar
		usuarioRepository.saveAll(Arrays.asList(u1));
		gradeRepository.saveAll(Arrays.asList(g1));
		usuarioRepository.flush();
		gradeRepository.flush();
		//
		
		gradeRepository.saveAll(Arrays.asList(g1));
		usuarioRepository.saveAll(Arrays.asList(u1));
	}

}
