package com.lurdharry.library.borrow;

import com.lurdharry.library.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;



    @PostMapping
    public ResponseEntity<ResponseDTO> borrowBook(
            @RequestBody @Valid BorrowRequest request
    ) {
        String id = borrowService.borrowBook(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "User details updated successfully.",id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<ResponseDTO> updateStatus(
            @RequestBody @Valid StatusRequest request
    ) {
        String id = borrowService.updateBorrowStatus(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "User details updated successfully.",id);
        return ResponseEntity.ok(response);
    }


}




//    @GetMapping("/{user-id}")
//    public ResponseEntity<ResponseDTO> getUser(@PathVariable("user-id") String userId) {
//        ResponseDTO response = borrowService.fetchUser(userId);
//        return ResponseEntity.ok(response);
//    }
