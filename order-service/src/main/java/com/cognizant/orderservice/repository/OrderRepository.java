package com.cognizant.orderservice.repository;

import com.cognizant.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    public List<Order> findByUserId(Integer id);
}
