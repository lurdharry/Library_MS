package com.lurdharry.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull(message = "user id is required")
        String id,

        @NotNull(message = "firstname is required")
        String firstname,

        @NotNull(message = "lastname is required")
        String lastname
) {
}
