package com.lurdharry.authorization.user;

import com.lurdharry.authorization.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotNull(message = "Password is required")
        String password,

        @NotNull(message = "Role is required")
        Role role
) {
}
