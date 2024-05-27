// package com.example.finance.controller;


// import com.example.finance.dto.auth.JwtAuthenticationResponse;
// import com.example.finance.dto.auth.SignInRequest;
// import com.example.finance.dto.auth.SignUpRequest;
// import com.example.finance.service.auth.AuthenticationService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/auth")
// @RequiredArgsConstructor
// @Tag(name = "Authentication")
// public class AuthenticationController {
//     private final AuthenticationService authenticationService;

//     @Operation(summary = "Registration")
//     @PostMapping("/sign-up")
//     public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
//         return authenticationService.signUp(request);
//     }

//     @Operation(summary = "Authorization")
//     @PostMapping("/sign-in")
//     public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
//         return authenticationService.signIn(request);
//     }
// }