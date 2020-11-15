package com.fortiumtech.scottdavies.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fortiumtech.scottdavies.domain.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>{

}
