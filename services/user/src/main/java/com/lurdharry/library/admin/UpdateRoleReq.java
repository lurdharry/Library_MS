package com.lurdharry.library.admin;

import com.lurdharry.library.user.Role;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateRoleReq(
        @NotNull(message = "User id is required")
        UUID userId,

        @NotNull(message = "role is required")
        Role role
) {
}
