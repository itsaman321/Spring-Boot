package com.cognizant.menuservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private Integer ingredient_id;
    private String ingredient_name;

    private Integer ingredient_quantity ;
}
