package com.cognizant.orderservice.controller;

import com.cognizant.orderservice.models.Order;
import com.cognizant.orderservice.repository.OrderRepository;
import com.cognizant.orderservice.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService ;

    @PostMapping("/{userId}")
    public String placeOrder(@RequestBody Order order, @PathVariable Integer userId){
        return orderService.placeOrder(order,userId);
    }
}
