package com.lurdharry.authorization.exception;

import com.lurdharry.authorization.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO> handleWrongPassword(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidException (MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String message =  error.getDefaultMessage();
            String fieldName =  ((FieldError) error).getField();

            errors.put(fieldName,message);
        } );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .data(errors)
                        .build()
        );

    }

}
