package com.fortiumtech.scottdavies.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
	@NonNull private Long id;
	@NonNull private String title;
	@NonNull private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NonNull private Author author;
	
}
