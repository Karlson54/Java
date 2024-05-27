package com.example.finance.mapper.registration;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.registration.RegistrationDTO;
import com.example.finance.model.StudentProfile;
import com.example.finance.model.TeacherProfile;
import com.example.finance.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistrationMapper {
    private final ModelMapper modelMapper;

    public User toUserEntity(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    public StudentProfile toStudentProfileEntity(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, StudentProfile.class);
    }

    public TeacherProfile toTeacherProfileEntity(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, TeacherProfile.class);
    }
}
