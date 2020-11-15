package com.fortiumtech.scottdavies.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBook {

	private Long id;
	private String title;
	private String description;
	private ApiAuthor auth;
	

}
