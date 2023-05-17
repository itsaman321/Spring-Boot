package com.cognizant.orderservice.services;

import com.cognizant.orderservice.models.Order;
import com.cognizant.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository ;

    public String placeOrder(Order order,Integer userId){
        order.setUserId(userId);
        orderRepository.save(order);
        return "Order Placed !" ;
    }
}
