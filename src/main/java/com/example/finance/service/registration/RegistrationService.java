package com.example.finance.service.registration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.controller.registration.JwtService;
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

    public String registerUser(RegistrationDTO registrationDTO) {
        User user = registrationMapper.toUserEntity(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(user);

        if (registrationDTO.getRole().equals("ROLE_STUDENT")) {
            StudentProfile studentProfile = registrationMapper.toStudentProfileEntity(registrationDTO);
            studentProfile.setUser(user);
            studentProfileRepository.save(studentProfile);
        } else if (registrationDTO.getRole().equals("ROLE_TEACHER")) {
            TeacherProfile teacherProfile = registrationMapper.toTeacherProfileEntity(registrationDTO);
            teacherProfile.setUser(user);
            teacherProfileRepository.save(teacherProfile);
        }
        return jwtService.generateToken(user);
    }
}
