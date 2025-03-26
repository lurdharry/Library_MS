package com.lurdharry.authorization.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        UUID id,
        String email,
        String firstname,
        String lastname,
        Role role,
        boolean verified
) {
}
