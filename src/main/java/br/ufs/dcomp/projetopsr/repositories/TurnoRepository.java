package br.ufs.dcomp.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.dcomp.projetopsr.domain.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
	
}