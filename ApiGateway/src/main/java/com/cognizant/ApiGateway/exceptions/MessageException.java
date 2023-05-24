package com.cognizant.ApiGateway.exceptions;

import lombok.Data;

@Data
public class MessageException extends RuntimeException{
    private String message ;
    public MessageException(String message){
        super(message);
        this.message = message ;
    }
}
