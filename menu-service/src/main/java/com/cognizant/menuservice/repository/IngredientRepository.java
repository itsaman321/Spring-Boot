package com.cognizant.menuservice.repository;

import com.cognizant.menuservice.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,Integer> {
}
