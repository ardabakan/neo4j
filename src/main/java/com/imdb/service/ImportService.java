package com.imdb.service;

public interface ImportService {

	void loadMovies() throws Exception;

	void loadPerson() throws Exception;

	void loadRatings() throws Exception;

	void loadRoles() throws Exception;
}
