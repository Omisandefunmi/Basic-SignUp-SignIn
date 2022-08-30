package com.example.BasicSignUpSignIn.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AddRoleToUserRequest {
    private String email;
    private String roleName;
}
