package br.ufs.dcomp.projetopsr.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufs.dcomp.projetopsr.domain.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
	@Query("SELECT t FROM Turno t JOIN Usuario u ON t.instituicao = u.instituicao WHERE t.id = ?1 AND u.id = ?2")
	Optional<Turno> findTurnoByUsuarioId(Integer turnoId, Integer usuarioId);
}