package com.lurdharry.book.book;

import com.lurdharry.book.dto.BookRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(@Valid BookRequest request) {
        return Book.builder()
                .title(request.title())
                .author(request.author())
                .quantity(request.quantity())
                .borrowedCopies(0)
                .build();
    }


    public BookResponse fromCustomer(Book savedBook) {
        return BookResponse.builder()
                .id(savedBook.getId())
                .author(savedBook.getAuthor())
                .title(savedBook.getTitle())
                .quantity(savedBook.getQuantity())
                .borrowedCopies(savedBook.getBorrowedCopies())
                .availableCopies(savedBook.getAvailableCopies())
                .build();
    }
}
