package com.lurdharry.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lurdharry.user.user.Role;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        String id,
        String email,
        String firstname,
        String lastname,
        Role role
) {
}
