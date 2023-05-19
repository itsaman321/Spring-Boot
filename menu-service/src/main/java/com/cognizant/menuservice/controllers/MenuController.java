package com.cognizant.menuservice.controllers;

import com.cognizant.menuservice.model.Inventory;
import com.cognizant.menuservice.model.Menu;
import com.cognizant.menuservice.requestDto.SuccessResponse;
import com.cognizant.menuservice.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/menu")
public class MenuController {
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<Menu>> retrieveAllItems(){
        return menuService.getAllItems() ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> retrieveItemById(@PathVariable Integer id){
        return menuService.getItemById(id);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createMenuItem(@RequestBody Menu menu){
        return menuService.createMenuItem(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateMenuItem(@PathVariable Integer id, @RequestBody Menu menu){
        return menuService.updateMenuItem(id , menu) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteMenuItem(@PathVariable Integer id){
        return menuService.deleteItem(id);
    }
}
