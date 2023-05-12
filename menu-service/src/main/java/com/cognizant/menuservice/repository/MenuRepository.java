package com.cognizant.menuservice.repository;

import com.cognizant.menuservice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
}
