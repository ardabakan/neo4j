package com.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imdb.service.ImportService;

@RestController
public class ImportController {

	@Autowired
	ImportService importService;

	@Transactional
	@RequestMapping("/api/reload")
	public ResponseEntity reload() {

		try {
			importService.loadMovies();
			System.out.println("Finished movies");
			//importService.loadPerson();
			System.out.println("Finished person");
			//importService.loadRoles();
			System.out.println("Finished roles");
			//importService.loadRatings();
			System.out.println("Finished ratings");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
