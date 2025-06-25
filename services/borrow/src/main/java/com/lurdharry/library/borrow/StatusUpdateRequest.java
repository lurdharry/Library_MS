package com.lurdharry.library.borrow;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(
        @NotNull(message = "admin id is required")
        @NotEmpty(message = "admin id is required")
        String adminId,

        @NotNull(message = "orderId is required")
        @NotEmpty(message = "orderId is required")
        String orderId
) {

}
