package com.lurdharry.library.borrow;

import com.lurdharry.library.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @GetMapping("/{user-id}")
    public ResponseEntity<ResponseDTO> getUser(@PathVariable("user-id") UUID userId) {
        ResponseDTO response = borrowService.fetchUser(userId);
        return ResponseEntity.ok(response);
    }
}
