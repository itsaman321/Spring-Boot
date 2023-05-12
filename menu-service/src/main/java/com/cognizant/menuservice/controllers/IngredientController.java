package com.cognizant.menuservice.controllers;

import com.cognizant.menuservice.model.Ingredient;
import com.cognizant.menuservice.repository.IngredientRepository;
import com.cognizant.menuservice.service.IngredientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/ingredient")
public class IngredientController {
    private IngredientService ingredientService ;

    @GetMapping
    public List<Ingredient> retrieveAllIngredient(){
        return ingredientService.getAllIngredients();
    }
}
