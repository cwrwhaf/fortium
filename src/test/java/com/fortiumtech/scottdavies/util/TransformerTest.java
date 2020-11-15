package com.fortiumtech.scottdavies.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.domain.Author;
import com.fortiumtech.scottdavies.domain.Book;

/**
 * @author scottdavies
 *
 *         These tests don't need any spring wiring so initializing objects
 *         manually
 */
public class TransformerTest {

	private static final String AUTHOR_LAST_NAME = "lastName";
	private static final String AUTHOR_FIRST_NAME = "firstName";
	private static final long AUTHOR_ID = 1L;

	private static final String BOOK_DESCRIPTION = "Description";
	private static final String BOOK_TITLE = "Title";
	private static final long BOOK_ID = 1L;
	

	@Test
	public void bookToApiBookTransformerTest() {

		Author author = new Author(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME);

		List<Book> books = new ArrayList<>();

		Book book = new Book(BOOK_ID, BOOK_TITLE, BOOK_DESCRIPTION, author);
		books.add(book);
		author.setBooks(books);

		BookToApiBookTransformer transformer = new BookToApiBookTransformer();
		ApiBook apiBook = transformer.transform(book);

		assertNotNull(apiBook, "ApiBook should not be null");
		assertEquals(apiBook.getId(), BOOK_ID, "ApiBook id should match Book id");
		assertEquals(apiBook.getTitle(), BOOK_TITLE, "ApiBook title should match Book title");
		assertEquals(apiBook.getDescription(), BOOK_DESCRIPTION, "ApiBook description should match Book description");
	}

	@Test
	public void apiBookToBookTransformerTest() {

		ApiAuthor apiAuthor = new ApiAuthor(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME, null);
		ApiBook apiBook = new ApiBook(BOOK_ID, BOOK_TITLE, BOOK_DESCRIPTION, apiAuthor);
		
		List<ApiBook> books = new ArrayList<>();
		books.add(apiBook);
		apiAuthor.setBooks(books);

		ApiBookToBookTransformer transformer = new ApiBookToBookTransformer(new ModelMapper());
		Book book = transformer.transform(apiBook);

		assertNotNull(book, "Book should not be null");
		assertEquals(book.getId(), BOOK_ID, "Book id should match Book id");
		assertEquals(book.getTitle(), BOOK_TITLE, "Book title should match Book title");
		assertEquals(book.getDescription(), BOOK_DESCRIPTION, "Book description should match Book description");
	}

	@Test
	public void authorToApiAuthorTransformerTest() {

		AuthorToApiAuthorTransformer transformer = new AuthorToApiAuthorTransformer();
		Author author = new Author(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME);
		
		
		List<Book> books = new ArrayList<>();
		books.add(new Book(BOOK_ID, BOOK_TITLE, BOOK_DESCRIPTION, author));
		author.setBooks(books);

		ApiAuthor apiAuthor = transformer.transform(author);

		assertNotNull(apiAuthor, "ApiAuthor should not be null");
		assertEquals(apiAuthor.getId(), AUTHOR_ID, "ApiAuthor id should match Author id");
		assertEquals(apiAuthor.getFirstName(), AUTHOR_FIRST_NAME,
				"ApiAuthor first name should match Author first name");
		assertEquals(apiAuthor.getLastName(), AUTHOR_LAST_NAME, "ApiAuthor last name should match Author last name");
		assertNotNull(apiAuthor.getBooks(), "ApiAuthor books should not be null");

		assertEquals(apiAuthor.getBooks().get(0).getTitle(), BOOK_TITLE, "ApiBooks should be populated");
	}

	@Test
	public void authorToApiAuthorTransformerNullBooksTest() {

		AuthorToApiAuthorTransformer transformer = new AuthorToApiAuthorTransformer();

		Author author = new Author(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME);

		ApiAuthor apiAuthor = transformer.transform(author);

		assertNotNull(apiAuthor, "ApiAuthor should not be null");
		assertEquals(apiAuthor.getId(), AUTHOR_ID, "ApiAuthor id should match Author id");
		assertEquals(apiAuthor.getFirstName(), AUTHOR_FIRST_NAME,
				"ApiAuthor first name should match Author first name");
		assertEquals(apiAuthor.getLastName(), AUTHOR_LAST_NAME, "ApiAuthor last name should match Author last name");
		assertNotNull(apiAuthor.getBooks(), "ApiAuthor books should not be null");

	}

	@Test
	public void apiAuthorToAuthorTransformerTest() {

		ApiAuthorToAuthorTransformer transformer = new ApiAuthorToAuthorTransformer(new ModelMapper());
		List<ApiBook> books = new ArrayList<>();
		
		ApiAuthor apiAuthor = new ApiAuthor(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME, books);

		Author author = transformer.transform(apiAuthor);

		assertNotNull(author, "Author should not be null");
		assertEquals(author.getId(), AUTHOR_ID, "Author id should match ApiAuthor id");
		assertEquals(author.getFirstName(), AUTHOR_FIRST_NAME, "Author first name should match ApiAuthor first name");
		assertEquals(author.getLastName(), AUTHOR_LAST_NAME, "Author last name should match ApiAuthor last name");
		assertNotNull(author.getBooks(), "Author books should not be null");
	}

	@Test
	public void apiAuthorToAuthorTransformerNullBooksTest() {

		ApiAuthorToAuthorTransformer transformer = new ApiAuthorToAuthorTransformer(new ModelMapper());

		ApiAuthor apiAuthor = new ApiAuthor(AUTHOR_ID, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME, null);

		Author author = transformer.transform(apiAuthor);

		assertNotNull(author, "Author should not be null");
		assertEquals(author.getId(), AUTHOR_ID, "Author id should match ApiAuthor id");
		assertEquals(author.getFirstName(), AUTHOR_FIRST_NAME, "Author first name should match ApiAuthor first name");
		assertEquals(author.getLastName(), AUTHOR_LAST_NAME, "Author last name should match ApiAuthor last name");
		assertNull(author.getBooks(), "Author books should not be null");

	}

}
