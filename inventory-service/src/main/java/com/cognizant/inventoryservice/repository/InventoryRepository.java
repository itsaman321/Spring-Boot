package com.cognizant.inventoryservice.repository;

import com.cognizant.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
}
