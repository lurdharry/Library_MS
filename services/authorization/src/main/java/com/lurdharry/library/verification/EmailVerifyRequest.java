package com.lurdharry.library.verification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailVerifyRequest(
        @NotNull(message = "email is required")
        @Email(message = "email must ve valid")
        String email
) {
}
