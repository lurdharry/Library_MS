package com.lurdharry.book.book;

import com.lurdharry.book.dto.BookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookMapper mapper;

    public BookResponse addBook(@Valid BookRequest request) {
        var book = mapper.toBook(request);
        var savedBook = repository.save(book);
        return mapper.fromCustomer(savedBook);
    }
}
