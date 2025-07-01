package com.lurdharry.library.book;

import com.lurdharry.library.dto.BookRequest;
import com.lurdharry.library.dto.UpdateBookRequest;
import com.lurdharry.library.exception.ResponseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookMapper mapper;

    public BookResponse addBook(@Valid BookRequest request) {
        var book = mapper.toBook(request);
        var savedBook = repository.save(book);
        return mapper.fromBook(savedBook);
    }

    public List<BookResponse> getBooks() {
        return repository.findAll().stream().map(mapper::fromBook).toList();
    }

    public BookResponse getBookById(String bookId) {
        var book= repository.findById(bookId).orElseThrow(()-> new ResponseException("Cannot find book",HttpStatus.BAD_REQUEST));

        return mapper.fromBook(book);
    }

    public Object updateBook(@Valid UpdateBookRequest request) {
        var book = repository.findById(request.id()).orElseThrow(()-> new ResponseException("Cannot find book",HttpStatus.BAD_REQUEST));

        var updatedBook = mapper.mergeBook(book,request);

        var savedBook = repository.save(updatedBook);

        return mapper.fromBook(savedBook);
    }

    public void delete(String bookId) {
        if (!repository.existsById(bookId)) {
            throw new ResponseException("Book not found", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(bookId);
    }

    public List<BookResponse> getBookByIds(@Valid List<String> bookIds){
        return repository.findAllById(bookIds).stream().map(mapper::toBorrowBookResponse).collect(Collectors.toList());
    }


    public List<BookResponse> borrowBook(@Valid List<String> bookIds) {
//        find all books
        var books = repository.findAllById(bookIds);

        // check if all books are in catalog
        if (bookIds.size() != books.size()){
            throw new ResponseException("One or more books is not available in catalog",HttpStatus.NOT_FOUND);
        }


        // borrowed book response details
        var borrowBookResponse = new ArrayList<BookResponse>();

        for (Book book : books){
            var availableCopies = book.getQuantity() - book.getBorrowedCopies();
            if (availableCopies < 1){
                throw new ResponseException("Insufficient quantity to borrow",HttpStatus.BAD_REQUEST);
            }

            book.setBorrowedCopies(book.getBorrowedCopies() + 1);
            borrowBookResponse.add(mapper.toBorrowBookResponse(book));
        }

        repository.saveAll(books);

        return borrowBookResponse;

    }
}
