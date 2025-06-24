package com.lurdharry.library.borrow;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class BorrowMapper {

    public BorrowOrder toBorrowOrder(@Valid BorrowRequest request) {
        return BorrowOrder.builder()
                .id(request.id())
                .userId(request.userId())
                .status(ApprovalStatus.PENDING)
                .build();
    }
}
