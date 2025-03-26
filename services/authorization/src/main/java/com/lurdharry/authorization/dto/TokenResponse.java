package com.lurdharry.authorization.dto;

import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String expiresIn
) {
}
