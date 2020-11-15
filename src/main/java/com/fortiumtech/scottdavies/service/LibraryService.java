package com.fortiumtech.scottdavies.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fortiumtech.scottdavies.api.exception.AlreadyExistsException;
import com.fortiumtech.scottdavies.api.exception.NotFoundException;
import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.api.rest.dto.ApiCreateAuthorRequest;
import com.fortiumtech.scottdavies.domain.Author;
import com.fortiumtech.scottdavies.domain.Book;
import com.fortiumtech.scottdavies.repository.AuthorRepository;
import com.fortiumtech.scottdavies.repository.BookRepository;
import com.fortiumtech.scottdavies.util.ApiBookToBookTransformer;
import com.fortiumtech.scottdavies.util.ApiCreateAuthorRequestToAuthorTransformer;
import com.fortiumtech.scottdavies.util.AuthorToApiAuthorTransformer;
import com.fortiumtech.scottdavies.util.BookToApiBookTransformer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class LibraryService {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorToApiAuthorTransformer authorTransformer;

	@Autowired
	private BookToApiBookTransformer bookTransformer;

	@Autowired
	private ApiCreateAuthorRequestToAuthorTransformer autorRequestTransformer;

	@Autowired
	private ApiBookToBookTransformer apiBookTransformer;

	public List<ApiAuthor> getAllAuthors(Integer page, Integer limit) {
		return authorRepository.findAll(PageRequest.of(page, limit)).stream().map(authorTransformer::transform)
				.collect(Collectors.toList());
	}

	public Optional<ApiAuthor> getAuthorById(long authorId) {
		return authorRepository.findById(authorId).map(authorTransformer::transform);
	}

	public List<ApiBook> getAllBooks(Integer page, Integer limit) {
		return bookRepository.findAll(PageRequest.of(page, limit)).stream().map(bookTransformer::transform)
				.collect(Collectors.toList());
	}

	public Optional<ApiBook> getBookById(Long bookId) {
		return bookRepository.findById(bookId).map(bookTransformer::transform);
	}

	public Author createAuthor(ApiCreateAuthorRequest author) {
		if (authorRepository.existsById(author.getId())) {
			log.error("Author {} already exists", author.getId());
			throw new AlreadyExistsException("Author already exists " + author.getId());
		}
		return authorRepository.save(autorRequestTransformer.transform(author));
	}

	public Long createBook(Long id, ApiBook apiBook) {

		return authorRepository.findById(id).map(author -> {

			Book book = apiBookTransformer.transform(apiBook);
			book.setAuthor(author);
			author.addBook(book);

			authorRepository.save(author);

			return apiBook.getId();
		}).orElseThrow(() -> new NotFoundException("Cannot find author " + id));
	}

}
