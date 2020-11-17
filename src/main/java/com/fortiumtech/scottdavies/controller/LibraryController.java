package com.fortiumtech.scottdavies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.api.rest.dto.ApiCreateAuthorRequest;
import com.fortiumtech.scottdavies.service.LibraryService;

@RestController
public class LibraryController {

	@Autowired
	private LibraryService libraryService;


	@GetMapping("/api/books")
	ResponseEntity<List<ApiBook>> getAllBooks(@RequestParam Integer page, @RequestParam Integer limit) {

		return ResponseEntity.ok(libraryService.getAllBooks(page, limit));
	}
	
	@GetMapping("/api/books/search")
	ResponseEntity<List<ApiBook>> searchBooks(@RequestParam String filterByGenre, @RequestParam Integer page, @RequestParam Integer limit) {

		return ResponseEntity.ok(libraryService.searchBooks(filterByGenre, page, limit));
	}

	@GetMapping("/api/books/{id}")
	ResponseEntity<ApiBook> getBooksById(@PathVariable("id") Long id) {

		return ResponseEntity.ok(libraryService.getBookById(id).orElseThrow(IllegalArgumentException::new));
	}

	@GetMapping("/api/authors")
	ResponseEntity<List<ApiAuthor>> getAllAuthors(@RequestParam Integer page, @RequestParam Integer limit) {

		return ResponseEntity.ok(libraryService.getAllAuthors(page, limit));
	}
	
	
	
	@GetMapping(path="/api/authorspro", params = {"projection"})
	ResponseEntity<List<?>> getAllAuthorsPojection(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String projection) {

		return ResponseEntity.ok(libraryService.getAllAuthorsProjection(page, limit, projection));
	}

	@GetMapping("/api/authors/{id}")
	ResponseEntity<ApiAuthor> getAuthorById(@PathVariable("id") Long id) {

		return ResponseEntity.ok(libraryService.getAuthorById(id).orElseThrow(IllegalArgumentException::new));
	}

	@PostMapping(path = "/api/authors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createAuthor(@RequestBody ApiCreateAuthorRequest author) {

		return new ResponseEntity<>(libraryService.createAuthor(author).getId(), HttpStatus.CREATED);
	}

	@PostMapping(path = "/api/authors/{id}/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createBook(@PathVariable("id") Long id, @RequestBody ApiBook book) {

		return new ResponseEntity<>(libraryService.createBook(id, book), HttpStatus.CREATED);
	}

}
