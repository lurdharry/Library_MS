package com.lurdharry.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRequest(
        @NotNull(message = "user id is required")
        UUID userId,

        @NotNull(message = "firstname is required")
        String firstname,

        @NotNull(message = "lastname is required")
        String lastname
) {
}
