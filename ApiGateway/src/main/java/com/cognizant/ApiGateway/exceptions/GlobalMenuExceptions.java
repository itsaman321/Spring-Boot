package com.cognizant.ApiGateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalMenuExceptions {
    //Message Exception
    @ExceptionHandler({MessageException.class})
    public ResponseEntity<ExceptionResponse> MessageException(MessageException msgEx){
        String msg = msgEx.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(msg , "false");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

}
