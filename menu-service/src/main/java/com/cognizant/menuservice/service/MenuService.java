package com.cognizant.menuservice.service;

import aj.org.objectweb.asm.TypeReference;
import com.cognizant.menuservice.exceptions.ResourceNotFoundException;
import com.cognizant.menuservice.model.Inventory;
import com.cognizant.menuservice.model.Menu;
import com.cognizant.menuservice.repository.MenuRepository;
import lombok.AllArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {
    private MenuRepository menuRepository ;
    private WebClient webClient ;
    public List<Menu> getAllItems(){
//       return menuRepository.findAll() ;
        List<Menu> menuItems = menuRepository.findAll();
        List<Inventory> fetchedIngredient =  webClient.get()
                .uri("http://localhost:8082/api/inventory")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Inventory>>() {})
                .block() ;


        for(Menu menu : menuItems){
            List<Inventory> finalList = new ArrayList<>();
            List<Integer> ingIds = menu.getIngredientIds();
            if(ingIds!=null){
                for(Integer id : ingIds){
                    for(Inventory fIng : fetchedIngredient){
                        if(id.equals(fIng.getIngredient_id())){
                            finalList.add(fIng);
                        }
                    }
                }
            }
            menu.setIngredients(finalList);
        }

        return menuItems ;
    }

    public String createMenuItem(Menu menu){

            menuRepository.save(menu);
            return "Menu Created Successfully" ;
    }

    public Optional<Menu> getItemById(Integer id) {
        return menuRepository.findById(id) ;
    }

    public String updateMenuItem(Integer id,Menu menu) {
        Menu updatedMenuItem = menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id"));
        updatedMenuItem.setItem_name(menu.getItem_name());
        updatedMenuItem.setItem_description(menu.getItem_description());
        updatedMenuItem.setItem_price(menu.getItem_price());

        menuRepository.save(updatedMenuItem);
        return "Menu Item Updated Successfully" ;
    }

    public String deleteItem(Integer id) {
        menuRepository.deleteById(id);
        return "Menu Item Deleted Success" ;
    }
}
