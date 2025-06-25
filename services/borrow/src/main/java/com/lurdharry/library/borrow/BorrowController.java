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


    @GetMapping("/{borrow-id}")
    public ResponseEntity<ResponseDTO> getOrderById(
            @PathVariable("borrow-id") String borrowId
    ){
        var res =  borrowService.getBorrowOrderById(borrowId);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Borrow order retrieved successfully",res);
        return  ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> borrowBook(
            @RequestBody @Valid BorrowRequest request
    ) {
        String res = borrowService.orderBook(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Book borrow successfully",res);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/allOrders")
    public ResponseEntity<ResponseDTO> getAllOrders(){
        var borrowOrders = borrowService.getAllBorrowOrders();
        ResponseDTO res = new ResponseDTO(HttpStatus.ACCEPTED.value(), null,borrowOrders);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/admin/approve")
    public ResponseEntity<ResponseDTO> approveBorrowStat(
            @RequestBody @Valid StatusUpdateRequest request
    ) {
        String res = borrowService.approveBorrowStatus(request);
        ResponseDTO response = new ResponseDTO(HttpStatus.ACCEPTED.value(), "Book borrow order status updated",res);
        return ResponseEntity.ok(response);
    }


}
