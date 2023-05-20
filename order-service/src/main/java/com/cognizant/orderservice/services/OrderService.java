package com.cognizant.orderservice.services;

import com.cognizant.orderservice.exceptions.MessageException;
import com.cognizant.orderservice.exceptions.ResourceNotFoundException;
import com.cognizant.orderservice.models.Inventory;
import com.cognizant.orderservice.models.Menu;
import com.cognizant.orderservice.models.MenuItemQuantity;
import com.cognizant.orderservice.models.Order;
import com.cognizant.orderservice.repository.OrderRepository;
import com.cognizant.orderservice.requestDto.SuccessResponse;
import com.cognizant.orderservice.requests.StatusRequest;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private WebClient webClient;

    public Mono<Boolean> checkIfMenuExist(Integer id) {
        return webClient.get().uri("http://localhost:8082/api/menu/" + id).exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return Mono.just(true);
            } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                return Mono.just(false);
            } else return Mono.just(false);

        });
    }

    public Menu fetchMenuById(Integer menuId) {
        Menu menu;
        try {
//            Mono<Boolean> exists = checkIfMenuExist(menuId);
//            if (exists.block()==false) {
//                throw new MessageException("Menu with Id : " + menuId + " does not Exist");
//            } else {
            menu = webClient.get()
                    .uri("http://localhost:8080/api/menu/" + menuId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Menu>() {
                    }).block();
//            }
        } catch (WebClientRequestException e) {
            throw new MessageException(e.getMessage());
        } catch (MessageException e) {
            throw new MessageException(e.getMessage());
        }
        return menu;
    }

    public ResponseEntity<SuccessResponse> placeOrder(Order order, Integer userId) {
        order.setUserId(userId);

        List<MenuItemQuantity> menuItemQuantities = order.getMenuItemQuantityList();

        Integer totalAmount = 0;

        for (MenuItemQuantity menuItemQuantity : menuItemQuantities) {

            Menu menu = fetchMenuById(menuItemQuantity.getMenu_id());

            //To check inventory for availability
            try {
                List<Integer> ingredientList = menu.getIngredientIds();
                for (Integer ingId : ingredientList) {
                    Integer itemQuantity;
                    try {
                        itemQuantity = webClient.get()
                                .uri("http://localhost:8082/api/inventory/quantity/" + ingId)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<Integer>() {
                                })
                                .block();
                    } catch (WebClientResponseException e) {
                        throw new MessageException(e.getMessage());
                    }
                    if (menuItemQuantity.getQuantity() > itemQuantity) {
                        throw new MessageException("Ingredient Item with Id : " + ingId + " is less quantity. Please check inventory");
                    }
                }
            } catch (MessageException e) {
                throw new MessageException(e.getMessage());
            }
            totalAmount += menu.getItem_price() * menuItemQuantity.getQuantity();
        }
        order.setOrderAmount(totalAmount);
        order.setOrderDate(LocalDate.now().toString());
        order.setOrderTime(LocalTime.now().format(DateTimeFormatter.ISO_TIME));
        orderRepository.save(order);
        return ResponseEntity.ok(new SuccessResponse("Order was successfully created for user : " + userId, HttpStatus.OK.toString()));
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<Order>();
        try{
            orders = orderRepository.findAll();
        }catch (MessageException e){
            throw new MessageException(e.getMessage());
        }

        for (Order order : orders) {
            List<MenuItemQuantity> menuItemQuantities = order.getMenuItemQuantityList();
            List<Menu> menuList = new ArrayList<>();

            for (MenuItemQuantity menuItemQuantity : menuItemQuantities) {
                Menu menu = fetchMenuById(menuItemQuantity.getMenu_id());
                if (menu != null) {
                    menuList.add(menu);
                }
                order.setMenuList(menuList);
            }
        }
        return orders;
    }


    public List<Order> getOrderByUser(@PathVariable Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        for (Order ord : orders) {
            List<MenuItemQuantity> menuItemQuantities = ord.getMenuItemQuantityList();
            List<Menu> menuItemList = new ArrayList<>();

            for (MenuItemQuantity menuItemQuantity : menuItemQuantities) {
                Menu menu = fetchMenuById(menuItemQuantity.getMenu_id());
                menuItemList.add(menu);
            }
            ord.setMenuList(menuItemList);
        }
        return orders;
    }

    public ResponseEntity<SuccessResponse> deleteOrder(Integer id) {
        try {
            orderRepository.deleteById(id);
        } catch (MessageException e) {
            throw new MessageException("Delete was unsuccessful");
        }
        return ResponseEntity.ok(new SuccessResponse("Order was successfully Deleted !", HttpStatus.OK.toString()));
    }

    public ResponseEntity<SuccessResponse> getStatusUpdate(StatusRequest status, Integer orderId) {
        Order order = new Order();
        try {
            order = orderRepository.findById(orderId).orElseThrow(() -> new MessageException("Id was not Found"));
        } catch (MessageException e) {
            throw new MessageException(e.getMessage());
        }
        order.setOrder_status(status.getStatus());
        orderRepository.save(order);
        return ResponseEntity.ok(new SuccessResponse("Status of Order : " + orderId + " was successfully updated.", HttpStatus.OK.toString()));
    }
}
