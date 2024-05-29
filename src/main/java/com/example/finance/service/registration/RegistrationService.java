package com.example.finance.service.registration;

import com.example.finance.dto.auth.SignInRequest;
import com.example.finance.model.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.service.auth.JwtService;
import com.example.finance.service.profile.ProfileService;
import com.example.finance.dto.registration.RegistrationDTO;
import com.example.finance.mapper.registration.RegistrationMapper;
import com.example.finance.model.StudentProfile;
import com.example.finance.model.TeacherProfile;
import com.example.finance.model.User;
import com.example.finance.repository.StudentProfileRepository;
import com.example.finance.repository.TeacherProfileRepository;
import com.example.finance.repository.UserRepository;
import com.example.finance.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final RegistrationMapper registrationMapper;

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String registerUser(RegistrationDTO registrationDTO) {
        User user = registrationMapper.toUserEntity(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(user);

        if (registrationDTO.getRole().equals(Role.ROLE_STUDENT)) {
            if (registrationDTO.getGroupId() == null) {
                throw new RuntimeException("Student without group");
            }
            StudentProfile studentProfile = registrationMapper.toStudentProfileEntity(registrationDTO);

            studentProfile.setUser(user);
            studentProfileRepository.save(studentProfile);
        } else if (registrationDTO.getRole().equals(Role.ROLE_TEACHER)) {
            if (registrationDTO.getFacultyId() == null) {
                throw new RuntimeException("Teached without faculty");
            }
            TeacherProfile teacherProfile = registrationMapper.toTeacherProfileEntity(registrationDTO);
            teacherProfile.setUser(user);
            teacherProfileRepository.save(teacherProfile);
        } else {
            throw new RuntimeException("Invalid role");
        }
        return jwtService.generateToken(user);
    }

    public String signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        return jwtService.generateToken(userDetails);
    }
}
