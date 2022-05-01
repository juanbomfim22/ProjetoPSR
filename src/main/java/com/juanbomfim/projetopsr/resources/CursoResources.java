package com.juanbomfim.projetopsr.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/cursos")
public class CursoResources {
	 
	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "Working";
	}
}
