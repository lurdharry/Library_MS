package com.lurdharry.library.dto;

import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String expiresIn
) {
}
