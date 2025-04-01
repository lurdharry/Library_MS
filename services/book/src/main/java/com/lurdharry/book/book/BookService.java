package com.lurdharry.book.book;

import com.lurdharry.book.dto.BookRequest;
import com.lurdharry.book.dto.UpdateBookRequest;
import com.lurdharry.book.exception.ResponseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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


    public List<BookBorrowResponse> borrowBook(@Valid List<BookBorrowRequest> request) {
//        get all ids from request
          var bookIds = request.stream().map(BookBorrowRequest::bookId).toList();

//        find all books
        var books = repository.findAllById(bookIds);

        // check if all books are in catalog
        if (bookIds.size() != books.size()){
            throw new ResponseException("One or more books is not available in catalog",HttpStatus.NOT_FOUND);
        }

        // sort request by bookId to be align with books in store
        var sortedReq = request.stream().sorted(Comparator.comparing(BookBorrowRequest::bookId)).toList();

        // borrowed book response details
        var borrowBookResponse = new ArrayList<BookBorrowResponse>();
        List<Book> booksToUpdate = new ArrayList<>();

        for (int i =0; i < sortedReq.size(); i++ ){
            var book = books.get(i);
            var bookReq = books.get(i);

            var availableCopies = book.getQuantity() - book.getBorrowedCopies();

            if (availableCopies < bookReq.getQuantity()){
                throw new ResponseException("Insufficient quantity to borrow",HttpStatus.BAD_REQUEST);
            }
            book.setBorrowedCopies(book.getBorrowedCopies() + bookReq.getQuantity());

            booksToUpdate.add(book);
            borrowBookResponse.add(mapper.toBorrowBookResponse(book));
        }

        repository.saveAll(booksToUpdate);

        return borrowBookResponse;

    }
}
