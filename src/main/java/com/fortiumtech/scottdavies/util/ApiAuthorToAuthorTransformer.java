package com.fortiumtech.scottdavies.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortiumtech.scottdavies.api.rest.dto.ApiAuthor;
import com.fortiumtech.scottdavies.domain.Author;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApiAuthorToAuthorTransformer implements Transformer<ApiAuthor, Author> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public Author transform(ApiAuthor apiAuthor) {
		return mapper.map(apiAuthor, Author.class);

	}

}
