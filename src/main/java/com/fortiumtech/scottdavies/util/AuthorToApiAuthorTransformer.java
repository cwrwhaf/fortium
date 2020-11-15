package com.fortiumtech.scottdavies.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.domain.Author;

@Component
public class AuthorToApiAuthorTransformer implements Transformer<Author, ApiAuthor> {

	@Override
	public ApiAuthor transform(Author author) {

		ApiAuthor apiAuthor = new ApiAuthor();
		apiAuthor.setId(author.getId());
		apiAuthor.setFirstName(author.getFirstName());
		apiAuthor.setLastName(author.getLastName());

		List<ApiBook> books = new ArrayList<>();
		author.getBooks().stream().forEach(b -> {
			ApiBook book = new ApiBook();
			book.setId(b.getId());
			book.setTitle(b.getTitle());
			books.add(book);
		});

		apiAuthor.setBooks(books);

		return apiAuthor;
	}

}
