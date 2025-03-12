package com.lurdharry.authorization.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDTO (
        HttpStatus status,
        String message,
        Object data
) {
}
