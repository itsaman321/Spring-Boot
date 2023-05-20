package com.cognizant.orderservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer order_id ;

    private Integer userId ;

    private String order_status ;

    private String orderTime ;

    private String orderDate ;

    private Integer orderAmount ;

    @OneToMany(cascade = CascadeType.ALL)
    List<MenuItemQuantity> menuItemQuantityList ;

    @Transient
    private List<Menu> menuList ;
}
