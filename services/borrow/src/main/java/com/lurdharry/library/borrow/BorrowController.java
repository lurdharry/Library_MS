package com.lurdharry.library.borrow;

import com.lurdharry.library.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;


    @PostMapping
    public ResponseEntity<ResponseDTO> borrowBook(
            @RequestBody @Valid BorrowRequest request
    ) {
        String res = borrowService.orderBook(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Book borrow successfully",res);
        return ResponseEntity.ok(response);
    }
}
