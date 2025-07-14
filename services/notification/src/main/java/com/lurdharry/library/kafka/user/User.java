package com.lurdharry.library.kafka.user;

public record User(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
