package com.lurdharry.library.borrow;

import com.lurdharry.library.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

//    @GetMapping("/{user-id}")
//    public ResponseEntity<ResponseDTO> getUser(@PathVariable("user-id") String userId) {
//        ResponseDTO response = borrowService.fetchUser(userId);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping
    public ResponseEntity<ResponseDTO> borrowBook(
            @RequestBody @Valid BorrowRequest request
    ) {
        ResponseDTO response = borrowService.borrowBook(request);
        return ResponseEntity.ok(response);
    }
}
