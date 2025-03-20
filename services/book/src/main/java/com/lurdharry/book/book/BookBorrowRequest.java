package com.lurdharry.book.book;

import jakarta.validation.constraints.NotNull;

public record BookBorrowRequest(
        @NotNull(message = "Book is mandatory")
        String bookId,

        @NotNull(message = "quantity is required")
        Integer quantity
) {
}
