package com.cognizant.orderservice.controller;

import com.cognizant.orderservice.models.Order;
import com.cognizant.orderservice.requestDto.SuccessResponse;
import com.cognizant.orderservice.requests.StatusRequest;
import com.cognizant.orderservice.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService ;

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrderByUser(@PathVariable Integer userId){
        return orderService.getOrderByUser(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse> placeOrder(@RequestBody Order order, @PathVariable Integer userId){
        return orderService.placeOrder(order,userId);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<SuccessResponse> updateOrderStatus(@RequestBody StatusRequest status, @PathVariable Integer orderId){
        return orderService.getStatusUpdate(status , orderId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteOrder(@PathVariable Integer id){
        return orderService.deleteOrder(id);
    }
}
