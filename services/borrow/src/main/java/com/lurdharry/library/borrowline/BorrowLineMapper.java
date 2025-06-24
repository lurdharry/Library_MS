package com.lurdharry.library.borrowline;

import com.lurdharry.library.borrow.BorrowOrder;
import org.springframework.stereotype.Service;

@Service
public class BorrowLineMapper {
    public BorrowLine toOrderLine(BorrowLineRequest request) {
        return BorrowLine.builder()
                .id(request.id())
                .bookId(request.bookId())
                .borrowOrder(
                        BorrowOrder.builder()
                                .id(request.borrowId())
                                .build()
                )
                .build();
    }
}
