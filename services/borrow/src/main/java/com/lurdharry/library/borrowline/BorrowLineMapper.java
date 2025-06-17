package com.lurdharry.library.borrowline;

import com.lurdharry.library.borrow.BorrowOrder;

public class BorrowLineMapper {
    public BorrowLine toBorrowLine(BorrowLineRequest request) {
        return BorrowLine.builder()
                .id(request.id())
                .bookId(request.bookId())
                .borrowOrder(BorrowOrder.builder()
                        .id(request.borrowId())
                        .build()
                )
                .build();
    }
}
