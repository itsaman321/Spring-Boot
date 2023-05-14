package com.cognizant.userservice.service;

import com.cognizant.userservice.exceptions.ResourceNotFoundException;
import com.cognizant.userservice.models.User;
import com.cognizant.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository ;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String createUser(User user) {
        userRepository.save(user);
        return "User Successfully Created" ;
    }

    public Optional<User> getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user ;
        }else{
            return null ;
        }
    }

    public String updateUserDetails(Integer id, User user) {
        User exUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Error: Not Found")) ;

            exUser.setUser_name(user.getUser_name());
            exUser.setUser_email(user.getUser_email());
            exUser.setUser_password(user.getUser_password());
            exUser.setUser_phone(user.getUser_phone());
            userRepository.save(exUser) ;
       return "updating user successful" ;


    }

    public String deleteUser(Integer id) {
         userRepository.deleteById(id);
         return "User Deleted Successfully having ID : " + id ;
    }
}
