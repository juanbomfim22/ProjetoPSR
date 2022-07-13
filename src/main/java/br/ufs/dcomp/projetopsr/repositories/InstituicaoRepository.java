package br.ufs.dcomp.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.dcomp.projetopsr.domain.Instituicao;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

}