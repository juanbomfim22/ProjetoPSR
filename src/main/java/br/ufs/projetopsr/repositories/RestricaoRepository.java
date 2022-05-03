package br.ufs.projetopsr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufs.projetopsr.domain.Restricao;

@Repository
public interface RestricaoRepository extends JpaRepository<Restricao, Integer> {

}