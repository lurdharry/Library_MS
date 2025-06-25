package com.lurdharry.library.borrow;
import com.lurdharry.library.book.BookResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BorrowOrderResponse(
        String id,

        ApprovalStatus status,

        String approvedBy,

        List<BookResponse> books,

        LocalDateTime borrowDate,

        LocalDateTime approvedDate,

        LocalDateTime dueDate
) {
}
