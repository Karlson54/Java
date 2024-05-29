package com.example.finance.controller.registration;

import com.example.finance.service.auth.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.dto.registration.RegistrationDTO;
import com.example.finance.model.Role;
import com.example.finance.service.registration.RegistrationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;


    @PostMapping("/student")
    public ResponseEntity<String> registerStudent(@RequestBody RegistrationDTO registrationDTO) {
        registrationDTO.setRole(Role.ROLE_STUDENT);
        return ResponseEntity.ok(registrationService.registerUser(registrationDTO));
    }

    @PostMapping("/teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody RegistrationDTO registrationDTO) {
        registrationDTO.setRole(Role.ROLE_TEACHER);
        return ResponseEntity.ok(registrationService.registerUser(registrationDTO));
    }


}
