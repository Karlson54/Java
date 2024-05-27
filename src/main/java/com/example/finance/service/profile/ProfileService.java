package com.example.finance.service.profile;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.dto.student.StudentProfileDTO;
import com.example.finance.dto.teacher.TeacherProfileDTO;
import com.example.finance.mapper.profile.ProfileMapper;
import com.example.finance.model.Faculty;
import com.example.finance.model.Group;
import com.example.finance.model.StudentProfile;
import com.example.finance.model.TeacherProfile;
import com.example.finance.model.User;
import com.example.finance.repository.FacultyRepository;
import com.example.finance.repository.GroupRepository;
import com.example.finance.repository.StudentProfileRepository;
import com.example.finance.repository.TeacherProfileRepository;
import com.example.finance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
    private final TeacherProfileRepository teacherProfileRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final GroupRepository groupRepository;
    private final ProfileMapper profileMapper;

    public List<TeacherProfileDTO> findAllTeachers() {
        return teacherProfileRepository.findAll().stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
    }

    public TeacherProfileDTO findTeacherById(Long id) {
        Optional<TeacherProfile> teacherProfile = teacherProfileRepository.findById(id);
        return teacherProfile.map(profileMapper::toDto).orElse(null);
    }

    public TeacherProfileDTO createTeacher(TeacherProfileDTO teacherProfileDTO) {
        User user = userRepository.findById(teacherProfileDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Faculty faculty = facultyRepository.findById(teacherProfileDTO.getFacultyId()).orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));
        TeacherProfile teacherProfile = profileMapper.toEntity(teacherProfileDTO, user, faculty);
        teacherProfile = teacherProfileRepository.save(teacherProfile);
        return profileMapper.toDto(teacherProfile);
    }

    public TeacherProfileDTO updateTeacher(Long id, TeacherProfileDTO teacherProfileDTO) {
        if (!teacherProfileRepository.existsById(id)) {
            return null;
        }
        User user = userRepository.findById(teacherProfileDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Faculty faculty = facultyRepository.findById(teacherProfileDTO.getFacultyId()).orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));
        TeacherProfile teacherProfile = profileMapper.toEntity(teacherProfileDTO, user, faculty);
        teacherProfile.setId(id);
        teacherProfile = teacherProfileRepository.save(teacherProfile);
        return profileMapper.toDto(teacherProfile);
    }

    public void deleteTeacher(Long id) {
        if (teacherProfileRepository.existsById(id)) {
            teacherProfileRepository.deleteById(id);
        }
    }

    public List<StudentProfileDTO> findAllStudents() {
        return studentProfileRepository.findAll().stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentProfileDTO findStudentById(Long id) {
        Optional<StudentProfile> studentProfile = studentProfileRepository.findById(id);
        return studentProfile.map(profileMapper::toDto).orElse(null);
    }

    public StudentProfileDTO createStudent(StudentProfileDTO studentProfileDTO) {
        User user = userRepository.findById(studentProfileDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Group group = groupRepository.findById(studentProfileDTO.getGroupId()).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        StudentProfile studentProfile = profileMapper.toEntity(studentProfileDTO, user, group);
        studentProfile = studentProfileRepository.save(studentProfile);
        return profileMapper.toDto(studentProfile);
    }

    public StudentProfileDTO updateStudent(Long id, StudentProfileDTO studentProfileDTO) {
        if (!studentProfileRepository.existsById(id)) {
            return null;
        }
        User user = userRepository.findById(studentProfileDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Group group = groupRepository.findById(studentProfileDTO.getGroupId()).orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        StudentProfile studentProfile = profileMapper.toEntity(studentProfileDTO, user, group);
        studentProfile.setId(id);
        studentProfile = studentProfileRepository.save(studentProfile);
        return profileMapper.toDto(studentProfile);
    }

    public void deleteStudent(Long id) {
        if (studentProfileRepository.existsById(id)) {
            studentProfileRepository.deleteById(id);
        }
    }
}
