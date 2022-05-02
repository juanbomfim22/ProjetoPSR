package br.ufs.projetopsr.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufs.projetopsr.domain.Curso;

@RestController
@RequestMapping(value="/cursos")
public class CursoResources {
	 
	@RequestMapping(method=RequestMethod.GET)
	public List<Curso> listar() {
		List<Curso> c = new ArrayList<>();
		c.add(new Curso(null, "A", "abc", 2, null));
		return c;
	}
}
