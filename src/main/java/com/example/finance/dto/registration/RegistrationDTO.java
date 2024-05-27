package com.example.finance.dto.registration;

import com.example.finance.model.Role;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
}
