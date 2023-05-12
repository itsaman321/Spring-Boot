package com.cognizant.menuservice.service;

import com.cognizant.menuservice.model.Menu;
import com.cognizant.menuservice.repository.MenuRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

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
            return "Success" ;
    }

    public Optional<Menu> getItemById(Integer id) {
        return menuRepository.findById(id) ;
    }
}
