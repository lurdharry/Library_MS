package com.lurdharry.library.book;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,String> {
    List<Book> findAllById(List<String> bookIds);
}
