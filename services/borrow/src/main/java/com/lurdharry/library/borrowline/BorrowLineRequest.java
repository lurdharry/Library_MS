package com.lurdharry.library.borrowline;


public record BorrowLineRequest(
        String id,
        String bookId,
        String borrowId
) {
}
