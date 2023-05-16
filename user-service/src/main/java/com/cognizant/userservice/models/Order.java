package com.cognizant.userservice.models;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class Order {
    private Integer order_id ;

    private Integer userId ;

    private String order_time ;

    private String order_date ;

    private String order_status ;

    private Integer order_amount ;

    private List<OrderItem> order_items ;

}
