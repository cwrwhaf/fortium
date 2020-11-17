package com.fortiumtech.scottdavies.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fortiumtech.scottdavies.domain.Author;
import com.fortiumtech.scottdavies.domain.Book;

/**
 * @author scottdavies
 * These tests test the author book mapping and query testing
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryTest {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Test
	void saveAndRetrieveAuthorByIdTest() {
		
		// Author must exist
		Author author = new Author(1L, "firstName", "lastName");
		authorRepository.save(author);
		
		
		Book book = new Book(1L, "title", "description", author);
		List<Book> books = new ArrayList<>();
		books.add(book);
		
		author.setBooks(books);
		authorRepository.save(author);
		assertNotNull(authorRepository.findById(author.getId()));
		assertNotNull(bookRepository.findById(book.getId()).get().getAuthor());
		assertNotNull(authorRepository.findById(author.getId()).get().getBooks());
	
	}
	

}
