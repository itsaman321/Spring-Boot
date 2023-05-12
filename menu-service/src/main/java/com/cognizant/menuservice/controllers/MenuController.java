package com.cognizant.menuservice.controllers;

import com.cognizant.menuservice.model.Ingredient;
import com.cognizant.menuservice.model.Menu;
import com.cognizant.menuservice.repository.MenuRepository;
import com.cognizant.menuservice.service.MenuService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/menu")
public class MenuController {
    private MenuService menuService;

    @GetMapping
    public List<Menu> retrieveAllItems(){
        return menuService.getAllItems() ;
    }

    @GetMapping("/{id}")
    public Optional<Menu> retrieveItemById(@PathVariable Integer id){
        return menuService.getItemById(id);
    }

    @PostMapping
    public String createMenuItem(@RequestBody Menu menu){
        return menuService.createMenuItem(menu);
    }
}
