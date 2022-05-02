package br.ufs.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.projetopsr.domain.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

}