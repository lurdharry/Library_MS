package com.lurdharry.library.book;

import com.lurdharry.library.dto.BookRequest;
import com.lurdharry.library.dto.UpdateBookRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

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


    public BookResponse fromBook(Book savedBook) {
        return BookResponse.builder()
                .id(savedBook.getId())
                .author(savedBook.getAuthor())
                .title(savedBook.getTitle())
                .quantity(savedBook.getQuantity())
                .borrowedCopies(savedBook.getBorrowedCopies())
                .build();
    }

    public Book mergeBook(Book book, @Valid UpdateBookRequest request) {
            updateIfNotBlank(book::setAuthor,request.author());
            updateIfNotBlank(book::setTitle,request.title());
            if (request.newCopiesCount() > 0){
                book.setQuantity(book.getQuantity() + request.newCopiesCount());
            }
            return book;
    }

    private void updateIfNotBlank(Consumer<String> setter, String value) {
        if (StringUtils.isNotBlank(value)) {
            setter.accept(value);
        }
    }

    public BookBorrowResponse toBorrowBookResponse(Book book) {
        return BookBorrowResponse.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .borrowedCopies(book.getBorrowedCopies())
                .quantity(book.getQuantity())
                .title(book.getTitle())
                .build();
    }
}
