package com.lurdharry.library.exception;

import com.lurdharry.library.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO> handleEmailAlreadyExists (EmailAlreadyExistsException exception){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseDTO> handleException (ResponseException exception){

        return ResponseEntity.status(exception.getStatus())
                .body(ResponseDTO.builder()
                        .status(exception.getStatusCode())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<ResponseDTO> handleInvalidToken(InvalidBearerTokenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(ex.getMessage())
                        .build()
                );
    }

}
