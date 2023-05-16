package com.cognizant.inventoryservice.service;

import com.cognizant.inventoryservice.exceptions.ResourceNotFoundException;
import com.cognizant.inventoryservice.models.Inventory;
import com.cognizant.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private InventoryRepository inventoryRepository ;
    public List<Inventory> getAllIngredient() {
        return inventoryRepository.findAll();
    }

    public Inventory getIngredientById(Integer id) {
        Inventory ingredient  = inventoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found"));
        return ingredient ;
    }

    public String createIngredient(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "Ingredient Successfully Created" ;
    }
}
