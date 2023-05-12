package com.cognizant.menuservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(columnDefinition = "serial")
    private Integer id ;
    private String item_name ;
    private String item_description ;
    private Integer item_price ;

    @ManyToMany
    private List<Ingredient> ingredients ;

}
