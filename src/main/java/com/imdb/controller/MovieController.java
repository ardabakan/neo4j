package com.imdb.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imdb.domain.Movie;
import com.imdb.service.impl.MovieServiceImpl;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	MovieServiceImpl movieService;

	@RequestMapping("/search/findByTitle") 
    public Movie findByTitle(@RequestParam String title){
        return movieService.getByTitle(title);
    }

    @RequestMapping("/search/findByTitleContaining")
    public Collection<Movie> findByTitleContaining(@RequestParam String title){
        return movieService.getByTitleContaining(title);
    }

    @RequestMapping("/topMovies")
    public Collection<Movie> findTopMoviesOfGenre(@RequestParam String genre, @RequestParam int limit){
        return movieService.findTopMoviesOfGenre(genre, limit);
    }
    
	@GetMapping("/loadMovies")
	public void loadMovies() {
		movieService.loadMovies();
	}

	@GetMapping("/movieCount")
	public Long movieCount() {
		return movieService.movieCount();
	}



	@GetMapping("/graph")
	public Map<String, Object> graph(
			@RequestParam(value = "limit", required = false) Integer limit) {
		return movieService.graph(limit == null ? 100 : limit);
	}
}
