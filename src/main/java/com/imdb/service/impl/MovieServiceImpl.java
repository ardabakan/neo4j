package com.imdb.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imdb.domain.Movie;
import com.imdb.domain.Person;
import com.imdb.domain.Role;
import com.imdb.repository.MovieRepository;
import com.imdb.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	private final static Logger LOG = LoggerFactory
			.getLogger(MovieServiceImpl.class);

	@Autowired
	MovieRepository movieRepository;

	public Map<String, Object> toD3Format(Collection<Movie> movies) {
		List<Map<String, Object>> nodes = new ArrayList<>();
		List<Map<String, Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<Movie> result = movies.iterator();
		while (result.hasNext()) {
			Movie movie = result.next();
			nodes.add(map("title", movie.getTitle(), "label", "movie"));
			int target = i;
			i++;
			for (Role role : movie.getRoles()) {
				Map<String, Object> actor = map("title",
						role.getPerson().getName(), "label", "actor");
				int source = nodes.indexOf(actor);
				if (source == -1) {
					nodes.add(actor);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
		}
		return map("nodes", nodes, "links", rels);
	}

	public Map<String, Object> map(String key1, Object value1, String key2,
			Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

	@Override
	public long movieCount() {

		long count = StreamSupport
				.stream(movieRepository.findAll().spliterator(), false).count();

		return count;

	}

	@Transactional(readOnly = true)
	public Movie getByTitle(String title) {
		Movie result = movieRepository.findByTitle(title);
		return result;
	}

	@Transactional(readOnly = true)
	public Collection<Movie> getByTitleContaining(String title) {
		Collection<Movie> result = movieRepository.findByTitleLike(title);
		return result;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> graph(int limit) {
		Collection<Movie> result = movieRepository.graph(limit);
		return toD3Format(result);
	}

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Transactional
	public void loadMovies() {

		List<Person> actors = new ArrayList<Person>();
		for (long i = 0; i < 100; i++) {
			Person p = new Person();
			p.setName("Actor Named " + i);
			// p.setId(i);

			actors.add(p);
		}

		// long iRoleId = 1;

		for (long i = 0; i < 100; i++) {

			Movie m = new Movie();

			m.setTitle("Some movie " + i);
			// m.setId(i);

			int actorsCountInThisMovie = getRandomNumberInRange(0, 5);

			for (long j = 0; j < actorsCountInThisMovie; j++) {
				Role r = new Role(m, actors.get(getRandomNumberInRange(0, 99)));
				// r.setId(iRoleId++);
				m.addRole(r);
			}

			movieRepository.save(m);

			long count = StreamSupport
					.stream(movieRepository.findAll().spliterator(), false)
					.count();

			System.out.println("Total element count is " + count + " and "
					+ m.getId() + " exists : "
					+ movieRepository.existsById(m.getId()));

		}

	}

	@Override
	public Collection<Movie> findTopMoviesOfGenre(String genre, int limit) {
		return movieRepository.topNMoviesOfGenre(genre, limit);
	}
}
