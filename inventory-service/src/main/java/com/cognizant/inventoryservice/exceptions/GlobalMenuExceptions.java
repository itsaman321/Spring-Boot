package com.cognizant.inventoryservice.exceptions;

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
        return ResponseEntity.ok(exceptionResponse);
    }

}
