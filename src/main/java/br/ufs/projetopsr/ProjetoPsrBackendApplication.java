package br.ufs.projetopsr;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.ufs.projetopsr.domain.Curso;
import br.ufs.projetopsr.domain.Disciplina;
import br.ufs.projetopsr.repositories.CursoRepository;
import br.ufs.projetopsr.repositories.DisciplinaRepository;

@SpringBootApplication
public class ProjetoPsrBackendApplication implements CommandLineRunner {

	@Autowired 
	private CursoRepository cursoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoPsrBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Curso c1 = new Curso(null,"Engenharia de Computacao", "EC", 6, "UFS");
		
		Disciplina d1 = new Disciplina(null, "Banco de Dados 1", "BD", 4);
		Disciplina d2 = new Disciplina(null, "Eng Software", "ES", 8);
		Disciplina d3 = new Disciplina(null, "Programacao Paralela", "PP", 4); 
		
		// Deve repetir o saveAll e usar o flush, se nao dá erro Transactional ...
		cursoRepository.saveAll(Arrays.asList(c1));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
		cursoRepository.flush();
		disciplinaRepository.flush();
		//
		
		c1.getDisciplinas().addAll(Arrays.asList(d1, d2, d3));
		
		d1.getCursos().addAll(Arrays.asList(c1));
		d2.getCursos().addAll(Arrays.asList(c1));
		d3.getCursos().addAll(Arrays.asList(c1));

		cursoRepository.saveAll(Arrays.asList(c1));
		disciplinaRepository.saveAll(Arrays.asList(d1, d2, d3));
		
	}

}
