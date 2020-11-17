package com.fortiumtech.scottdavies.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fortiumtech.scottdavies.domain.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>{

	Page<Book>  findByTitleContaining(Pageable page, String filter);

}