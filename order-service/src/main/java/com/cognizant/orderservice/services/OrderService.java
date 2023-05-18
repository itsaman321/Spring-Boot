package com.cognizant.orderservice.services;

import com.cognizant.orderservice.exceptions.ResourceNotFoundException;
import com.cognizant.orderservice.models.Menu;
import com.cognizant.orderservice.models.Order;
import com.cognizant.orderservice.repository.OrderRepository;
import com.cognizant.orderservice.requests.StatusRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository ;
    private WebClient webClient ;

    public Menu fetchMenuById(Integer menuId){
        Menu menu = webClient.get()
                .uri("http://localhost:8080/api/menu/"+menuId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Menu>() {
                }).block() ;
        return menu ;
    }

    public String placeOrder(Order order,Integer userId){
        order.setUserId(userId);
        List<Integer> menuIds = order.getMenuItemsIds();
        Integer totalAmount = 0 ;

        for(Integer menuId : menuIds){
            Menu menu = fetchMenuById(menuId);
            totalAmount += menu.getItem_price();
        }
        order.setOrderAmount(totalAmount);
        order.setOrderDate(LocalDate.now().toString());
        order.setOrderTime(LocalTime.now().format(DateTimeFormatter.ISO_TIME));
        orderRepository.save(order);
        return order.getOrder_id().toString() ;
    }

    public List<Order> getAllOrders() {
       List<Order> orders = orderRepository.findAll() ;
       for(Order order : orders){
           List<Integer> menuIds = order.getMenuItemsIds();
           List<Menu> menuList = new ArrayList<>() ;
           Integer totalAmount = 0 ;
           for(Integer menuId : menuIds){
               Menu menu = fetchMenuById(menuId);
               if(menu!=null){
                   menuList.add(menu) ;
                   totalAmount += menu.getItem_price();
               }
           }
           order.setMenuList(menuList);
           order.setOrderAmount(totalAmount);
       }
        return orders ;
    }


    public List<Order> getOrderByUser(@PathVariable Integer userId){
        List<Order> orders = orderRepository.findByUserId(userId) ;
        for(Order ord : orders){
            List<Integer> menuIds = ord.getMenuItemsIds();
            List<Menu> menuItemList = new ArrayList<>() ;

            for(Integer id : menuIds){
                Menu menu = fetchMenuById(id);
                menuItemList.add(menu);
            }
            ord.setMenuList(menuItemList);
        }
        return orders ;
    }

    public String deleteOrder(Integer id) {
        orderRepository.deleteById(id);
        return "Order Deleted Successfully" ;
    }

    public String getStatusUpdate(StatusRequest status, Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Error ! Not Found"));
        order.setOrder_status(status.getStatus());
        orderRepository.save(order);
        return "Order Status Updated ! " ;
    }
}
