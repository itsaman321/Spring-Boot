package com.cognizant.inventoryservice.controller;

import com.cognizant.inventoryservice.models.Inventory;
import com.cognizant.inventoryservice.requestDto.SuccessResponse;
import com.cognizant.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {
    private InventoryService inventoryService ;
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllIngredient(){
        return inventoryService.getAllIngredient();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getIngredientById(@PathVariable Integer id){
        return inventoryService.getIngredientById(id);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createIngredient(@RequestBody Inventory ing){
        return inventoryService.createIngredient(ing);
    }


}
