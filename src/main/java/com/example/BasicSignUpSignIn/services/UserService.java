package com.example.BasicSignUpSignIn.services;

import com.example.BasicSignUpSignIn.data.models.User;
import com.example.BasicSignUpSignIn.data.models.Role;
import com.example.BasicSignUpSignIn.dtos.requests.AddRoleRequest;
import com.example.BasicSignUpSignIn.dtos.requests.AddRoleToUserRequest;
import com.example.BasicSignUpSignIn.dtos.requests.SignUpRequest;
import com.example.BasicSignUpSignIn.web.exception.UserAlreadyExistsException;
import com.example.BasicSignUpSignIn.web.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    User register(SignUpRequest request) throws UserAlreadyExistsException;
    Role saveRole(AddRoleRequest request);
    void addRoleToUser(AddRoleToUserRequest request) throws UserNotFoundException;
    User getUser(String username);
    List<User> getAllUSer();
}
