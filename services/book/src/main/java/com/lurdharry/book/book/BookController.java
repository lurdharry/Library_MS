package com.lurdharry.book.book;

import com.lurdharry.book.dto.BookRequest;
import com.lurdharry.book.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> createBook(
            @RequestBody @Valid BookRequest request
    ){

      var addedBook =  service.addBook(request);
      ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Book added successfully",addedBook);
      return ResponseEntity.ok(response);
    }

}
