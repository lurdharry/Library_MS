package com.lurdharry.authorization.user;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        String id,
        String email,
        String firstname,
        String lastname,
        String role,
        boolean verified
) {
}
