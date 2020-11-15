package com.fortiumtech.scottdavies.util;

import org.springframework.stereotype.Component;

import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.domain.Book;

@Component
public class BookToApiBookTransformer implements Transformer<Book, ApiBook> {
	
	@Override
	public ApiBook transform(Book book) {

		ApiBook apiBook = new ApiBook();
		apiBook.setId(book.getId());
		apiBook.setTitle(book.getTitle());
		apiBook.setDescription(book.getDescription());
			
		ApiAuthor author = new ApiAuthor();
		author.setId(book.getAuthor().getId());
		author.setFirstName(book.getAuthor().getFirstName());
		author.setLastName(book.getAuthor().getLastName());
		apiBook.setAuth(author);
		return apiBook;
	}

}
