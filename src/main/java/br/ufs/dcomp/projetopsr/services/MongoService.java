package br.ufs.dcomp.projetopsr.services;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.repositories.MongoRepository;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@Service
public class MongoService {

	@Autowired
	private MongoRepository repo;

	public Document buscar(String collection, Integer nth) {
		Document obj = repo.getMongoClient().getCollection(collection).find().skip(nth).first();
		if(obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + nth);
		}
		return obj;
		}
}
