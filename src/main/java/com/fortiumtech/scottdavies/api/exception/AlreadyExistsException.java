package com.fortiumtech.scottdavies.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 8056748247745512913L;
// this message isn't getting through, look at creating an error handler
	public AlreadyExistsException(String message) {
		super(message);
	}
	
}
