package com.cognizant.menuservice.service;

import com.cognizant.menuservice.exceptions.ExceptionResponse;
import com.cognizant.menuservice.exceptions.MessageException;
import com.cognizant.menuservice.model.Inventory;
import com.cognizant.menuservice.model.Menu;
import com.cognizant.menuservice.repository.MenuRepository;
import com.cognizant.menuservice.requestDto.SuccessResponse;
import lombok.AllArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class MenuService {
    private MenuRepository menuRepository;
    private WebClient webClient;

    public boolean hasNotNullAttributes(Inventory inventory) {
        Field[] fields = inventory.getClass().getDeclaredFields();
        Boolean flag = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value == null) flag = false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public ResponseEntity<List<Menu>> getAllItems() {

        List<Menu> menuItems = menuRepository.findAll();
        List<Inventory> fetchedIngredient = new ArrayList<>() ;
        try{
            fetchedIngredient = webClient.get().uri("http://localhost:8082/api/inventory").retrieve().bodyToMono(new ParameterizedTypeReference<List<Inventory>>() {
            }).block();
        }catch(WebClientRequestException e){
            throw new MessageException(e.getMessage());
        }


        for (Menu menu : menuItems) {
            List<Inventory> finalList = new ArrayList<>();
            List<Integer> ingIds = menu.getIngredientIds();
            if (ingIds != null) {
                for (Integer id : ingIds) {
                    for (Inventory fIng : fetchedIngredient) {
                        if (id.equals(fIng.getIngredient_id())) {
                            finalList.add(fIng);
                        }
                    }
                }
            }
            menu.setIngredients(finalList);
        }

        return ResponseEntity.ok(menuItems);
    }

    //To check if the Ingredient exists;
    public Mono<Boolean> checkIfInventoryExist(Integer id) {
        return webClient.get().uri("http://localhost:8082/api/inventory/" + id).exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return Mono.just(true);
            } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                return Mono.just(false);
            } else return Mono.just(false);

        });
    }

    public ResponseEntity<SuccessResponse> createMenuItem(Menu menu) {
        List<Integer> ingredientIds = menu.getIngredientIds();

        for (Integer id : ingredientIds) {
            //Checking if Ingredient is available in inventory
            Mono<Boolean> itemExist = checkIfInventoryExist(id);
            if (Boolean.FALSE.equals(itemExist.block())) {
                throw new MessageException("Ingredient With Id : " + id + " was Not found");
            }
        }
        try{
            menuRepository.save(menu);
        }catch (MessageException e){
            throw new MessageException(e.getMessage());
        }
        return ResponseEntity.ok(new SuccessResponse("Menu Item Created Successfully", HttpStatus.OK.toString()));
    }

    public ResponseEntity<Menu> getItemById(Integer id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new MessageException("Menu Item With Id : " + id + " does not exists.."));
        List<Inventory> ingredientsList = new ArrayList<>();
        List<Integer> idArr = menu.getIngredientIds();
        for (Integer ingId : idArr) {
            try {
                Inventory inventoryItem = webClient.get()
                        .uri("http://localhost:8082/api/inventory/" + ingId).retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Inventory>() {
                        })
                        .block();
                ingredientsList.add(inventoryItem);
            } catch (WebClientResponseException exception) {
                throw new MessageException(exception.getMessage());
            }
        }
        menu.setIngredients(ingredientsList);
        return ResponseEntity.ok(menu);
    }

    public ResponseEntity<SuccessResponse> updateMenuItem(Integer id, Menu menu) {
        Menu updatedMenuItem = menuRepository.findById(id).orElseThrow(() -> new MessageException("Menu Item does not exist with id"));
        updatedMenuItem.setItem_name(menu.getItem_name());
        updatedMenuItem.setItem_description(menu.getItem_description());
        updatedMenuItem.setItem_price(menu.getItem_price());
        updatedMenuItem.setIngredientIds(menu.getIngredientIds());
        try {
            menuRepository.save(updatedMenuItem);
        } catch (MessageException e) {
            throw new MessageException("Unable to connect to Menu Repository !");
        }
        return ResponseEntity.ok(new SuccessResponse("Menu Item Updated Successfully", HttpStatus.OK.toString()));
    }

    public ResponseEntity<SuccessResponse> deleteItem(Integer id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok( new SuccessResponse("Menu Item Deleted Success", HttpStatus.OK.toString()));
    }
}
