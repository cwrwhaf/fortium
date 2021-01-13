package com.fortiumtech.scottdavies.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortiumtech.scottdavies.domain.Author;
import com.fortiumtech.scottdavies.domain.Book;
import com.fortiumtech.scottdavies.repository.AuthorRepository;
import com.fortiumtech.scottdavies.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LibraryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@BeforeEach
	public void setup() {
		// Author must exist
		Author author = new Author(1L, "firstName", "lastName");
		//authorRepository.save(author);

		Book book = new Book(1L, "title", "description", author);
		List<Book> books = new ArrayList<>();
		books.add(book);

		author.setBooks(books);
		authorRepository.save(author);
	}

	@Test
	void getAuthorById() throws Exception {

		mockMvc.perform(get("/api/authors/{id}", 1L)).andExpect(jsonPath("$.firstName", equalTo("firstName")))
				.andExpect(status().isOk());

		Optional<Author> author = authorRepository.findById(1L);
		assertTrue(author.isPresent(), "Author should be returned");
		assertEquals(author.get().getFirstName(), "firstName", "Author should match");
	}

	@Test
	void getAllAuthors() throws Exception {

		mockMvc.perform(get("/api/authors").param("page", "0").param("limit", "5"))
				.andExpect(jsonPath("$.[0]firstName", equalTo("firstName"))).andExpect(status().isOk());

		Optional<Author> author = authorRepository.findById(1L);
		assertTrue(author.isPresent(), "Author should be returned");
		assertEquals(author.get().getFirstName(), "firstName", "Author should match");
	}

	@Test
	void getBookById() throws Exception {

		mockMvc.perform(get("/api/books/{id}", 1L)).andExpect(jsonPath("$.title", equalTo("title")))
				.andExpect(status().isOk());

		Optional<Book> book = bookRepository.findById(1L);
		assertTrue(book.isPresent(), "Book should be returned");
		assertEquals(book.get().getTitle(), "title", "Book should match");
	}

	
	//@Test
	void getAllBooks() throws Exception {

		mockMvc.perform(get("/api/books").param("page", "0").param("limit", "5"))
				.andExpect(jsonPath("$.[0]title", equalTo("title"))).andExpect(status().isOk());

		Optional<Book> book = bookRepository.findById(1L);
		assertTrue(book.isPresent(), "Book should be returned");
		assertEquals(book.get().getTitle(), "title", "Book should match");
	}
	
	// post calls to do

}
