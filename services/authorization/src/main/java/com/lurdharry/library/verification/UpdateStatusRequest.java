package com.lurdharry.authorization.verification;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull(message = "email is required")
        String email,

        @NotNull(message = "OTP is required")
        String code
) {
}
