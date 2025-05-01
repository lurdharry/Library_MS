package com.lurdharry.library.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseException extends RuntimeException   {
   private final String message;
   private final HttpStatus status;
   private final Integer statusCode;

   public ResponseException (String msg, HttpStatus status){
      this.message=msg;
      this.status = status;
      this.statusCode = status.value();
   }
}
