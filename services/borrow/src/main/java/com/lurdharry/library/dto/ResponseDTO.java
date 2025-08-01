package com.lurdharry.library.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDTO<T>(
        Integer status,
        String message,
        T data
) {
}
