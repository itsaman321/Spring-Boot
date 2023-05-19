package com.cognizant.inventoryservice.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {
    private String message ;
    private String responseStatus ;
}
