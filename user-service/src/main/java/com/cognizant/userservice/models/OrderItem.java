package com.cognizant.userservice.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Integer order_item_id ;

    private Integer order_id ;

    private Integer order_item_name ;

    private Integer order_item_quantity;

    private Integer order_item_price ;

}
