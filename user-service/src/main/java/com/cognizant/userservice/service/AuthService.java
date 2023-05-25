package com.cognizant.userservice.service;

import com.cognizant.userservice.exceptions.MessageException;
import com.cognizant.userservice.models.User;
import com.cognizant.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepository ;

    private PasswordEncoder passwordEncoder ;

    private JwtService jwtService ;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String createUser(User user) {
        user.setUser_password(passwordEncoder.encode(user.getUser_password()));
        try{
            userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new MessageException(e.getMessage());
        }
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
        User exUser = userRepository.findById(id).orElseThrow(()-> new MessageException("Error: Not Found")) ;

            exUser.setName(user.getName());
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

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
