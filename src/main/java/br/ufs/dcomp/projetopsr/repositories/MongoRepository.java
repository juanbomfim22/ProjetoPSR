package br.ufs.dcomp.projetopsr.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoRepository {
	
	@Value("${spring.data.mongodb.uri}")
	private String uri;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	public MongoDatabase getMongoClient() {
		try {
 			return MongoClients.create(uri).getDatabase(database);
		} catch (Exception e) {
			
		}
		return null;
	}
}
