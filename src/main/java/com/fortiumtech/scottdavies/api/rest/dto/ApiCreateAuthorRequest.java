package com.fortiumtech.scottdavies.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCreateAuthorRequest {
	private Long id;
	private String firstName;
	private String lastName;
}

