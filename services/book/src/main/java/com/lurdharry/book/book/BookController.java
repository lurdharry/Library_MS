package com.lurdharry.book.book;

import com.lurdharry.book.dto.BookRequest;
import com.lurdharry.book.dto.ResponseDTO;
import com.lurdharry.book.dto.UpdateBookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createBook(
            @RequestBody @Valid BookRequest request
    ){

      var addedBook =  service.addBook(request);
      ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Book added successfully",addedBook);
      return ResponseEntity.ok(response);
    }

    @PostMapping("/borrow")
    public ResponseEntity<ResponseDTO> borrowBook(
            @RequestBody @Valid List<BookBorrowRequest> request
    ){
        var res =  service.borrowBook(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Book borrow successfully",res);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('VIEW_BOOKS')")
    public ResponseEntity<ResponseDTO> getBooks(

    ){
        var books =  service.getBooks();
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Books retrieved successfully",books);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<ResponseDTO> getBook(
            @PathVariable("book-id") String bookId
    ){
        var book = service.getBookById(bookId);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Book info retrieved successfully",book);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateBook(
             @RequestBody @Valid UpdateBookRequest request
    ){
        var updatedBook = service.updateBook(request);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Book details updated successfully",updatedBook);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{book-id}")
    public ResponseEntity<ResponseDTO> deleteBook (
            @PathVariable("book-id") String bookId
    ){
        service.delete(bookId);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.NO_CONTENT.value(), "Book deleted",null));
    }



}
