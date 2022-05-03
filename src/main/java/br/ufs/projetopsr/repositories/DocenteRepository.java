package br.ufs.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.projetopsr.domain.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {

}