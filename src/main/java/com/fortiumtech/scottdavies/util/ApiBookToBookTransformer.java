package com.fortiumtech.scottdavies.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortiumtech.scottdavies.api.rest.dto.ApiBook;
import com.fortiumtech.scottdavies.domain.Book;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApiBookToBookTransformer implements Transformer<ApiBook, Book> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Book transform(ApiBook apiBook) {

		return mapper.map(apiBook, Book.class);

	}

}
