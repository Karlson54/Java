package com.example.finance.mapper.registration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.example.finance.dto.registration.RegistrationDTO;
import com.example.finance.model.Faculty;
import com.example.finance.model.Group;
import com.example.finance.model.StudentProfile;
import com.example.finance.model.TeacherProfile;
import com.example.finance.model.User;

import lombok.RequiredArgsConstructor;

@Component

public class RegistrationMapper {
    private final ModelMapper modelMapper;

    public RegistrationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(RegistrationDTO.class, StudentProfile.class)
                .addMappings(mapper -> {
                    mapper.skip(StudentProfile::setId); 
                    mapper.skip(StudentProfile::setUser);
                    mapper.<Long>map(RegistrationDTO::getGroupId, (dest, v) -> {
                        Group group = new Group();
                        group.setId(v);
                        dest.setGroup(group);
                    });
                });

        modelMapper.createTypeMap(RegistrationDTO.class, TeacherProfile.class)
                .addMappings(mapper -> {
                    mapper.skip(TeacherProfile::setId);
                    mapper.<Long>map(RegistrationDTO::getFacultyId, (dest, v) -> {
                        Faculty faculty = new Faculty();
                        faculty.setId(v);
                        dest.setFaculty(faculty);
                    });
                });
    }

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
