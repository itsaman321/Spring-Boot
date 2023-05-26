package com.cognizant.orderservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private Integer user_id ;


    private String name;

    private String user_email ;

    private String user_phone ;

    private String user_password ;

}
