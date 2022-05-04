package br.ufs.projetopsr.repositories;

import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoRepository {
	
	private final String uri = "mongodb+srv://iclass-backend:OR1SMvj6dNSfmdea@estudos.g99vo.gcp.mongodb.net";
	private final String database = "iclass";
	
	public MongoDatabase getMongoClient() {
		try {
			return MongoClients.create(uri).getDatabase(database);
		} catch (Exception e) {
			
		}
		return null;
	}
}
