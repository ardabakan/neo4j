package com.imdb.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.imdb.domain.Movie;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

	Movie findByTitle(@Param("title") String title);

	Collection<Movie> findByTitleLike(@Param("title") String title);
	
	Movie findByOriginalId(@Param("originalId") String originalId);
	
	//@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
	@Query("MATCH (m:Movie) WHERE any(x IN m.genres WHERE x IN [genre])	RETURN m LIMIT {limit}")	
	Collection<Movie> topNMoviesOfGenre(@Param("genre") String genre, @Param("limit") int limit);

	@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
	Collection<Movie> graph(@Param("limit") int limit);
}