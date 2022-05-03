package br.ufs.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.projetopsr.domain.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

}