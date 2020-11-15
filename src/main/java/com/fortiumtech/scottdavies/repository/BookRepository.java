package com.fortiumtech.scottdavies.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fortiumtech.scottdavies.domain.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>{

}