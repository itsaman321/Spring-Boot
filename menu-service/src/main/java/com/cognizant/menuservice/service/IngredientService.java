package com.cognizant.menuservice.service;

import com.cognizant.menuservice.model.Ingredient;
import com.cognizant.menuservice.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;
    public List<Ingredient> getAllIngredients() {
       return ingredientRepository.findAll() ;
    }
}
