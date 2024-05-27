// package com.example.finance.service.auth;

// import com.example.finance.dto.auth.JwtAuthenticationResponse;
// import com.example.finance.dto.auth.SignInRequest;
// import com.example.finance.dto.auth.SignUpRequest;
// import com.example.finance.model.Role;
// import com.example.finance.model.User;
// import com.example.finance.service.user.UserService;

// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// @RequiredArgsConstructor
// public class AuthenticationService {
//     private final UserService userService;
//     private final JwtService jwtService;
//     private final PasswordEncoder passwordEncoder;
//     private final AuthenticationManager authenticationManager;

//     public JwtAuthenticationResponse signUp(SignUpRequest request) {

//         User user = new User()
//                 .setUsername(request.getUsername())
//                 .setPassword(passwordEncoder.encode(request.getPassword()))
//                 .setRole(Role.ADMIN);

//         userService.create(user);

//         String jwt = jwtService.generateToken(user);
//         return new JwtAuthenticationResponse(jwt);
//     }

//     public JwtAuthenticationResponse signIn(SignInRequest request) {
//         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                 request.getUsername(),
//                 request.getPassword()));

//         UserDetails userDetails = userService
//                 .userDetailsService()
//                 .loadUserByUsername(request.getUsername());

//         String jwt = jwtService.generateToken(userDetails);
//         return new JwtAuthenticationResponse(jwt);
//     }
// }