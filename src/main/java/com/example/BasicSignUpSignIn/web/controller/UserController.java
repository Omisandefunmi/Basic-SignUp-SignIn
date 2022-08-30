package com.example.BasicSignUpSignIn.web.controller;

import com.example.BasicSignUpSignIn.dtos.requests.AddRoleRequest;
import com.example.BasicSignUpSignIn.dtos.requests.AddRoleToUserRequest;
import com.example.BasicSignUpSignIn.dtos.requests.SignUpRequest;
import com.example.BasicSignUpSignIn.services.UserService;
import com.example.BasicSignUpSignIn.web.exception.UserAlreadyExistsException;
import com.example.BasicSignUpSignIn.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
     private final UserService userService;

     @GetMapping("/searchAll")
     public ResponseEntity <?> getAllUsers(){
         return new ResponseEntity<>(userService.getAllUSer(), HttpStatus.OK);
     }

     @PostMapping("/signup")
    public ResponseEntity <?> registerUser(SignUpRequest request){
         try {
             return new ResponseEntity<>(userService.register(request), HttpStatus.CREATED);
         } catch (UserAlreadyExistsException e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
     }



    @PostMapping("/role/save")
    public ResponseEntity <?> saveRole(AddRoleRequest request){
         URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
            return ResponseEntity.created(uri).body(userService.saveRole(request));
    }

    @PostMapping("/user/addRole")
    public ResponseEntity <?> addRole(AddRoleToUserRequest request) throws UserNotFoundException {
        userService.addRoleToUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
