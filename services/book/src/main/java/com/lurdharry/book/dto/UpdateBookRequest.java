package com.lurdharry.book.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateBookRequest(
        String id,

        @NotNull(message = "author is required")
        String author,

        @NotNull(message = "title is required")
        String title,

        @NotNull(message = "new supply quantity is required")
        @Positive(message = "new supply quantity must be a positive number")
        Integer newCopiesCount
) {
}
