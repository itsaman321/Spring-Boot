package com.cognizant.userservice.controller;


import com.cognizant.userservice.UserServiceApplication;
import com.cognizant.userservice.models.User;
import com.cognizant.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService ;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @PostMapping
    public String createNewUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public String updateUser(@RequestBody User user , @PathVariable Integer id){
        return userService.updateUserDetails(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }
}
