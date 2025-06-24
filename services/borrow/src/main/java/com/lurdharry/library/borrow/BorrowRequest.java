package com.lurdharry.library.borrow;

import jakarta.validation.constraints.*;

import java.util.List;

public record BorrowRequest(
        String id,

        @NotNull(message = "User is required")
        @NotEmpty(message = "User is required")
        @NotBlank(message = "User is required")
        String userId,

        @NotEmpty(message = "You should at least borrow one book")
        List<String> books
) {
}
