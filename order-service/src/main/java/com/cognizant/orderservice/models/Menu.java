package com.cognizant.orderservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu {

    private Integer id ;
    private String item_name ;
    private String item_description ;
    private Integer item_price ;

    private List<InventoryItem> ingredientItemList ;


}
