package com.lurdharry.library.verification;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull(message = "email is required")
        String email,

        @NotNull(message = "OTP is required")
        String code
) {
}
