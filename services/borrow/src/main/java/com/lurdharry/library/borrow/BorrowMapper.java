package com.lurdharry.library.borrow;

import com.lurdharry.library.book.BookBorrowRequest;
import com.lurdharry.library.borrowline.BorrowLine;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class BorrowMapper {

    public BorrowOrder toBorrowOrder(@Valid BorrowRequest request) {
        return BorrowOrder.builder()
                .id(request.id())
                .reference(request.reference())
                .userId(request.userId())
                .status(ApprovalStatus.PENDING)
                .build();
    }

    public BookBorrowRequest toBookBorrowrequest(BorrowLine borrowLine) {
        return BookBorrowRequest.builder()
                .bookId(borrowLine.getBookId())
                .build();
    }
}
