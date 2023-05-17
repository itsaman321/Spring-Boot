package com.cognizant.orderservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id ;
    private Integer userId ;

    private String order_status ;

    private List<Integer> MenuItemsIds ;

    @Transient
    private List<MenuItem> menuList ;
}
