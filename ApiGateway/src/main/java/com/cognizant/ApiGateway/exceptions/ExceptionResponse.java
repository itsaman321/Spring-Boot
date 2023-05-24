package com.cognizant.ApiGateway.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String msg ;
    private String success ;
}
