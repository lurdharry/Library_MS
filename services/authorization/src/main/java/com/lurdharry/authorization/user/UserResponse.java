package com.lurdharry.authorization.user;

public record UserResponse(
        String email,
        String firstname,
        String lastname
) {
}
