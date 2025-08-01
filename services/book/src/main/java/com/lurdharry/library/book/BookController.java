package com.lurdharry.library.book;

import com.lurdharry.library.dto.BookRequest;
import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.dto.UpdateBookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestBody @NotEmpty List<String> request
    ){
        var res =  service.borrowBook(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Book borrow successfully",res);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/by-ids")
    public ResponseEntity<ResponseDTO> getBooksByIds(
            @RequestBody @NotEmpty List<String> request
    ){
        var res = service.getBookByIds(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.ACCEPTED.value(), "Books retrieved successfully",res);
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

    @PostMapping("/return")
    public ResponseEntity<ResponseDTO> returnBooks(@Valid List<String> bookIds){
        service.returnBooks(bookIds);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(),"Books returned successfully",null));
    }
}
