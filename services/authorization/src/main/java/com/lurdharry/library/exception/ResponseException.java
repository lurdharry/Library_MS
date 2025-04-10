package com.lurdharry.library.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseException extends RuntimeException {
    private final String message;
    private final Integer statusCode;
    private final HttpStatus status;

    public ResponseException(String message, HttpStatus status){
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
    }
}
