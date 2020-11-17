package com.fortiumtech.scottdavies.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
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
	
	@Autowired
	private ProjectionFactory projectionFactory;

	
	interface Auth {
		@Value("#{target.firstName + ' ' + target.lastName}")
	    String getFullName();
	}
	interface Name {
		@Value("#{target.firstName}")
	    String getFirstName();
	}
	
	public List<ApiAuthor> getAllAuthors(Integer page, Integer limit) {
		return authorRepository.findAll(PageRequest.of(page, limit)).stream().map(authorTransformer::transform)
				.collect(Collectors.toList());
	}

	private Map<String, Class<?>> projections;
	
	@PostConstruct
	private void init() {
		projections = new HashMap<>();
		projections.put("auth", Auth.class);
		projections.put("name", Name.class);
	}
	
	
	public List<?> getAllAuthorsProjection(Integer page, Integer limit, String projection) {
		return authorRepository.findAll(PageRequest.of(page, limit)).stream()
				.map(a -> projectionFactory.createProjection(projections.get(projection), a)).collect(Collectors.toList());
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

	public List<ApiBook>  searchBooks(String filter, Integer page, Integer limit) {
		Page<Book> response = bookRepository.findByTitleContaining(PageRequest.of(page, limit), filter);
		System.out.println(response);
		return null;
	}

}
