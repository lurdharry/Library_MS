package com.lurdharry.library.borrowline;

public record BorrowLineRequest(
        String id,
        String borrowId,
        String bookId
) {

}
