package com.fortiumtech.scottdavies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.domain.Author;
import com.fortiumtech.scottdavies.domain.Book;
import com.fortiumtech.scottdavies.repository.AuthorRepository;
import com.fortiumtech.scottdavies.repository.BookRepository;
import com.fortiumtech.scottdavies.util.ApiBookToBookTransformer;
import com.fortiumtech.scottdavies.util.ApiCreateAuthorRequestToAuthorTransformer;
import com.fortiumtech.scottdavies.util.AuthorToApiAuthorTransformer;
import com.fortiumtech.scottdavies.util.BookToApiBookTransformer;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
public class LibraryServiceTest {

	private static final Long AUTHOR_ID = 1L;
	private static final String AUTHOR_LAST_NAME = "lastName";
	private static final String AUTHOR_FIRST_NAME = "firstName";
	private static final Long BOOK_ID = 1L;
	private static final String BOOK_TITLE = "title";
	private static final String BOOK_DESCRIPTION = "book";
	private Author author;
	private Book book;
	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private BookRepository bookRepository;

	@Autowired
	private AuthorToApiAuthorTransformer authorTransformer;

	@Autowired
	private BookToApiBookTransformer bookTransformer;

	@Autowired
	private ApiCreateAuthorRequestToAuthorTransformer autorRequestTransformer;

	@Autowired
	private ApiBookToBookTransformer apiBookTransformer;

	private LibraryService libraryService;

	@BeforeEach
	public void setup() {
		libraryService = new LibraryService(authorRepository, bookRepository, authorTransformer, bookTransformer,
				autorRequestTransformer, apiBookTransformer);
		author = new Author(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME);
		book = new Book(BOOK_ID, BOOK_TITLE, BOOK_DESCRIPTION, author);
	}

	@Test
	void getAllAuthorsTest() {
		List<Author> authors = new ArrayList<>();
		authors.add(author);
		Page<Author> pagedResponse = new PageImpl<Author>(authors);

		when(authorRepository.findAll(PageRequest.of(1, 4))).thenReturn(pagedResponse);

		List<ApiAuthor> apiAuthors = libraryService.getAllAuthors(1, 4);

		assertFalse(apiAuthors.isEmpty());

	}

	@Test
	void getAuthorByIdTest() {

		when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(author));

		Optional<ApiAuthor> apiAuthor = libraryService.getAuthorById(AUTHOR_ID);
		assertTrue(apiAuthor.isPresent(), "Author should be returned");
		assertEquals(apiAuthor.get().getId(), AUTHOR_ID, "Author id should match");

	}

	@Test
	void getAllBooksTest() {
		List<Book> books = new ArrayList<>();
		books.add(book);
		Page<Book> pagedResponse = new PageImpl<Book>(books);

		when(bookRepository.findAll(PageRequest.of(0, 5))).thenReturn(pagedResponse);

		List<ApiBook> apiBooks = libraryService.getAllBooks(0, 5);

		assertFalse(apiBooks.isEmpty());

	}

	@Test
	void getBookByIdTest() {

		when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

		Optional<ApiBook> apiBook = libraryService.getBookById(BOOK_ID);
		assertTrue(apiBook.isPresent(), "Book should be returned");
		assertEquals(apiBook.get().getId(), BOOK_ID, "Book id should match");

	}

	// save author
	// save existing author - should error

	// save book
	// save book when author doesn't exist - should error

}
