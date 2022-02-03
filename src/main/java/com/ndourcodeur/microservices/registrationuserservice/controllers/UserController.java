package com.ndourcodeur.microservices.registrationuserservice.controllers;

import com.ndourcodeur.microservices.registrationuserservice.message.Message;
import com.ndourcodeur.microservices.registrationuserservice.models.User;
import com.ndourcodeur.microservices.registrationuserservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/auth/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *   URL ===>  http://localhost:8100/api/auth/users/all
     */
    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> fetchAllUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            return new ResponseEntity<>(new Message("Sorry, User's empty"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     *   URL ===>  http://localhost:8100/api/auth/users/{id}
     */
    @GetMapping(path = "/{idUser}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> fetchUserById(@PathVariable Long idUser){
        if (!userRepository.existsById(idUser)){
            return new ResponseEntity<>(new Message("User not found with ID:"+idUser), HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(idUser).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     *   URL ===>  http://localhost:8100/api/auth/users/{id}
     */
    @DeleteMapping(path = "/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable Long idUser){
        if (!userRepository.existsById(idUser)){
            return new ResponseEntity<>(new Message("User not found with ID:"+idUser), HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(idUser).get();
        userRepository.delete(user);
        return new ResponseEntity<>(new Message("User deleted successfully."), HttpStatus.OK);
    }
}
