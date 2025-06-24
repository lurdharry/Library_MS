package com.lurdharry.library.book;

import jakarta.validation.constraints.NotNull;

public record BookBorrowRequest(
        @NotNull(message = "Book is mandatory")
        String bookId
) {
}
