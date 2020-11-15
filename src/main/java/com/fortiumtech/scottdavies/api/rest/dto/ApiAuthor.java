package com.fortiumtech.scottdavies.api.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiAuthor {
	private Long id;
	private String firstName;
	private String lastName;
	private List<ApiBook> books;
}

