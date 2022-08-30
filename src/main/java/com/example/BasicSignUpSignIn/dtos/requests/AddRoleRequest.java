package com.example.BasicSignUpSignIn.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AddRoleRequest {
    private String roleName;
}
