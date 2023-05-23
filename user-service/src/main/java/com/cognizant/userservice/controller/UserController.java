package com.cognizant.userservice.controller;


import com.cognizant.userservice.dto.AuthRequest;
import com.cognizant.userservice.exceptions.MessageException;
import com.cognizant.userservice.models.User;
import com.cognizant.userservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private AuthService authService;

    private AuthenticationManager authenticationManager ;

    @GetMapping
    public List<User> getAllUser(){
        return authService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id){
        return authService.getUser(id);
    }

    @PostMapping("/register")
    public String createNewUser(@RequestBody User user){
        return authService.createUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if(authenticate.isAuthenticated()){
                return authService.generateToken(authRequest.getUsername());
            }else{
                throw new MessageException("Invalid Access !");
            }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "token is valid !";
    }

    @PutMapping("/{id}")
    public String updateUser(@RequestBody User user , @PathVariable Integer id){
        return authService.updateUserDetails(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id){
        return authService.deleteUser(id);
    }
}
