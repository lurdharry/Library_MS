package com.lurdharry.library.book;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BookBorrowRequest(
        @NotNull(message = "Book is mandatory")
        String bookId
) {
}
