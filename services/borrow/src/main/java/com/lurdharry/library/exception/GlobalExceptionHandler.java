package com.lurdharry.library.exception;

import com.lurdharry.library.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseDTO> Res (ResponseException exception){

        return ResponseEntity.status(exception.getStatus())
                .body(ResponseDTO.builder()
                        .status(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build()
                );
    }
}
