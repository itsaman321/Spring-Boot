package com.cognizant.orderservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    private Integer id ;
    private String item_name ;
    private String item_description ;
    private Integer item_price ;


    private List<Integer> ingredientIds ;


    @JsonIgnore
    private List<Inventory> ingredients;
}
