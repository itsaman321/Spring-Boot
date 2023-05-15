package com.cognizant.menuservice.service;

import com.cognizant.menuservice.exceptions.ResourceNotFoundException;
import com.cognizant.menuservice.model.Menu;
import com.cognizant.menuservice.repository.MenuRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {
    private MenuRepository menuRepository ;

    public List<Menu> getAllItems(){
       return menuRepository.findAll() ;
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
        updatedMenuItem.setIngredients(menu.getIngredients());

        menuRepository.save(updatedMenuItem);
        return "Menu Item Updated Successfully" ;
    }

    public String deleteItem(Integer id) {
        menuRepository.deleteById(id);
        return "Menu Item Deleted Success" ;
    }
}
