package com.cognizant.inventoryservice.service;

import com.cognizant.inventoryservice.exceptions.MessageException;
import com.cognizant.inventoryservice.models.Inventory;
import com.cognizant.inventoryservice.repository.InventoryRepository;
import com.cognizant.inventoryservice.requestDto.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {
    private InventoryRepository inventoryRepository ;
    public ResponseEntity<List<Inventory>> getAllIngredient() {
        try{
            List<Inventory> inventories  =  inventoryRepository.findAll();
            return ResponseEntity.ok(inventories);

        }catch (MessageException msgEx){
            throw new MessageException("Unable to Fetch the Items. Check you connection !");
        }
    }

    public ResponseEntity<Inventory> getIngredientById(Integer id) {
        Inventory ingredient  = inventoryRepository.findById(id).orElseThrow(()-> new MessageException("User with Id "+ id + " was not found !"));
        return ResponseEntity.ok(ingredient) ;
    }

    public ResponseEntity<SuccessResponse> createIngredient(Inventory inventory) {
        try{
            inventoryRepository.save(inventory);
        }catch (MessageException messageException){
            throw new MessageException("Unable to Save Data to Database : Connection Refused");
        }
        return ResponseEntity.ok(new SuccessResponse("Ingredient Created Successfully with id : "
                 + inventory.getIngredient_id(), HttpStatus.OK.toString())) ;
    }

    public Integer getQuantityByItem(Integer id) {
        Inventory inventory ;
        try{
            inventory = inventoryRepository.findById(id).orElseThrow(()->new MessageException("Not found"));
        }catch (MessageException ex){
            throw new MessageException("Ingredient with id :" + id + " not found !");
        }

        return inventory.getIngredient_quantity() ;
    }
}
