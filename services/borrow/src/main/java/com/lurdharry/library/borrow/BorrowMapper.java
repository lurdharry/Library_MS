package com.lurdharry.library.borrow;

import com.lurdharry.library.book.BookResponse;
import com.lurdharry.library.borrowline.BorrowLine;
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

    public BookResponse toBookResponse (BorrowLine borrowLine){
        return BookResponse.builder()
                .bookId(borrowLine.getBookId())
                .build();
    }

    public BorrowOrderResponse toBorrowOrderResponse(BorrowOrder borrowOrder) {
        return BorrowOrderResponse.builder()
                .id(borrowOrder.getId())
                .borrowDate(borrowOrder.getBorrowDate())
                .status(borrowOrder.getStatus())
                .approvedBy(borrowOrder.getApprovedBy())
                .dueDate(borrowOrder.getDueDate())
                .approvedDate(borrowOrder.getApprovedDate())
                .books(borrowOrder.getBorrowLines().stream().map(this::toBookResponse).toList())
                .build();
    }
}
