package com.imdb.service.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.AppProperties;
import com.imdb.domain.Movie;
import com.imdb.domain.Person;
import com.imdb.domain.Rating;
import com.imdb.domain.Role;
import com.imdb.repository.MovieRepository;
import com.imdb.repository.PersonRepository;
import com.imdb.service.ImportService;

@Service
public class FileBasedImportServiceImpl implements ImportService {

	private final static Logger logger = LoggerFactory
			.getLogger(FileBasedImportServiceImpl.class);

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	Session session;

	@Autowired
	private AppProperties appProperties;

	@Override
	public void loadPerson() throws Exception {

		long counter = 0;

		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(

					appProperties.getFolderDirectory() + Person.TSV_PATH);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine() && counter < 500) {

				String line = sc.nextLine();

				if (counter == appProperties.getMaxLines()) {
					break;
				}
				if (counter++ == 0) {
					continue;
				}

				Person p = null;

				try {

					p = new Person();

					p.readFromLine(line);

					personRepository.save(p);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

	}

	@Override
	public void loadMovies() throws Exception {

		long counter = 0;

		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(
					appProperties.getFolderDirectory() + Movie.TSV_PATH);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (counter == appProperties.getMaxLines()) {
					break;
				}
				if (counter++ == 0) {
					continue;
				}

				Movie m = null;

				try {

					m = new Movie();

					m.readFromLine(line);

					movieRepository.save(m);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

	}

	@Override
	public void loadRatings() throws Exception {
		
		

		List<Rating> allRatings = new ArrayList<Rating>();

		long counter = 0;

		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(
					appProperties.getFolderDirectory() + Rating.TSV_PATH);
			sc = new Scanner(inputStream, "UTF-8");

			while (sc.hasNextLine()) {

				String line = sc.nextLine();
				if (counter == appProperties.getMaxLines()) {
					break;
				}
				if (counter++ == 0) {
					continue;
				}

				Rating r = null;

				try {

					r = new Rating();

					r.readFromLine(line);

					allRatings.add(r);

				} catch (Exception e) {
					logger.error("Problem loading ratings : " + e.getMessage());
				}

			}

			// after all ratings are read, send the bulk update request

			// WITH {data} AS pairs UNWIND pairs AS p MATCH (m:Movie) WHERE
			// m.originalId = p.movieId SET m.rating = p.rating

			ObjectMapper om = new ObjectMapper();
			String data = om.writeValueAsString(allRatings);

			String q = "WITH " + data
					+ " AS pairs UNWIND pairs AS p MATCH (m:Movie) WHERE m.originalId = p.movieId SET m.rating = p.rating";

			session.query(q, Collections.EMPTY_MAP);

			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

	}

	@Override
	public void loadRoles() throws Exception {

		long counter = 0;

		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(
					appProperties.getFolderDirectory() + Role.TSV_PATH);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (counter == appProperties.getMaxLines()) {
					break;
				}
				if (counter++ == 0) {
					continue;
				}

				Role r = null;

				try {

					r = new Role();

					r.readFromLine(line);

					if (r.getCategory() != null
							&& (r.getCategory().equals("actor")
									|| r.getCategory().equals("self"))) {

						Movie m = movieRepository
								.findByOriginalId(r.getOriginalMovieId());
						Person p = personRepository
								.findByOriginalId(r.getOriginalPersonId());

						if (m != null && p != null) {

							r.setPerson(p);
							r.setMovie(m);

							m.addRole(r);

							movieRepository.save(m);

						}

					}

				} catch (Exception e) {
					logger.error("Problem loading roles : " + e.getMessage());
				}
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

	}
}
