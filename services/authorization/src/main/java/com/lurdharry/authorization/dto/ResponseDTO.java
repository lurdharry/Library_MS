package com.lurdharry.authorization.dto;


import org.springframework.http.HttpStatus;

public record ResponseDTO (
        HttpStatus statusCode,
        String message
) {

}
