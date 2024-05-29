package com.example.finance.mapper.profile;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.student.StudentProfileDTO;
import com.example.finance.dto.teacher.TeacherProfileDTO;
import com.example.finance.model.Faculty;
import com.example.finance.model.Group;
import com.example.finance.model.StudentProfile;
import com.example.finance.model.TeacherProfile;
import com.example.finance.model.User;

@Component
public class ProfileMapper {
    private final ModelMapper modelMapper;

    public ProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeacherProfileDTO toDto(TeacherProfile teacherProfile) {
        TeacherProfileDTO dto = modelMapper.map(teacherProfile, TeacherProfileDTO.class);
        dto.setUserId(teacherProfile.getUser().getId());
        if (teacherProfile.getFaculty() != null) {
            dto.setFacultyId(teacherProfile.getFaculty().getId());
        }
        return dto;
    }

    public TeacherProfile toEntity(TeacherProfileDTO teacherProfileDTO, User user, Faculty faculty) {
        TeacherProfile teacherProfile = modelMapper.map(teacherProfileDTO, TeacherProfile.class);
        teacherProfile.setUser(user);
        teacherProfile.setFaculty(faculty);
        return teacherProfile;
    }

    public StudentProfileDTO toDto(StudentProfile studentProfile) {
        StudentProfileDTO dto = modelMapper.map(studentProfile, StudentProfileDTO.class);
        dto.setUserId(studentProfile.getUser().getId());
        if (studentProfile.getGroup() != null) {
            dto.setGroupId(studentProfile.getGroup().getId());
        }
        return dto;
    }

    public StudentProfile toEntity(StudentProfileDTO studentProfileDTO, User user, Group group) {
        StudentProfile studentProfile = modelMapper.map(studentProfileDTO, StudentProfile.class);
        studentProfile.setUser(user);
        studentProfile.setGroup(group);
        return studentProfile;
    }
}
