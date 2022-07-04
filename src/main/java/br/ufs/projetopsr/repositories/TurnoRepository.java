package br.ufs.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.projetopsr.domain.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
	
}