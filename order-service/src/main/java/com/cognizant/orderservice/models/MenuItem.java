package com.cognizant.orderservice.models;

import java.util.List;

public class MenuItem {
    private Integer id ;
    private String item_name ;
    private String item_description ;
    private Integer item_price ;


    private List<Integer> ingredientIds ;
}
