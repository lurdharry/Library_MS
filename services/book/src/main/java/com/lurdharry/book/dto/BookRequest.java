package com.lurdharry.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookRequest(
        String id,

        @NotNull(message = "author is required")
        String author,

        @NotNull(message = "title is required")
        String title,

        @NotNull(message = "quantity is required")
        @Positive(message = "Quantity must be a positive number")
        Integer quantity
) {
}
