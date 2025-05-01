package com.lurdharry.library.book;

import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotNull(message = "Book id is required")
        String bookId
) {
}
