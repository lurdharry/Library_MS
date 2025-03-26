package com.lurdharry.authorization.admin;

import com.lurdharry.authorization.user.Role;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateRoleReq(
        @NotNull(message = "User id is required")
        UUID userId,

        @NotNull(message = "role is required")
        Role role
) {
}
