package com.lurdharry.library.kafka;

import lombok.Builder;

@Builder
public record User(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
