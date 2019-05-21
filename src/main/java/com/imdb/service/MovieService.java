package com.imdb.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imdb.domain.Movie;

@Service
public interface MovieService {

	public Map<String, Object> toD3Format(Collection<Movie> movies);

	public Map<String, Object> map(String key1, Object value1, String key2,
			Object value2);

	@Transactional(readOnly = true)
	public Movie getByTitle(String title);

	@Transactional(readOnly = true)
	public Collection<Movie> getByTitleContaining(String title);

	@Transactional(readOnly = true)
	public Collection<Movie> findTopMoviesOfGenre(String genre, int limit);
	
	@Transactional(readOnly = true)
	public Map<String, Object> graph(int limit);

	@Transactional
	public void loadMovies();
	
	@Transactional(readOnly = true)
	public long movieCount();

}
