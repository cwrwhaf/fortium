package com.fortiumtech.scottdavies.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortiumtech.scottdavies.api.rest.dto.ApiCreateAuthorRequest;
import com.fortiumtech.scottdavies.domain.Author;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApiCreateAuthorRequestToAuthorTransformer implements Transformer<ApiCreateAuthorRequest, Author> {
	@Autowired
	private ModelMapper mapper;

	@Override
	public Author transform(ApiCreateAuthorRequest apiAuthor) {

		return mapper.map(apiAuthor, Author.class);

	}
}
