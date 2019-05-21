package com.imdb.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.imdb.domain.Person;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

	Person findByName(String name);
	
	Person findByOriginalId(@Param("originalId") String originalId);

}