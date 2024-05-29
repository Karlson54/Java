package com.example.finance;

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
import com.example.finance.service.profile.ProfileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    @Mock
    private TeacherProfileRepository teacherProfileRepository;

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllTeachers() {
        TeacherProfile teacherProfile = new TeacherProfile();
        TeacherProfileDTO teacherProfileDTO = new TeacherProfileDTO();
        when(teacherProfileRepository.findAll()).thenReturn(List.of(teacherProfile));
        when(profileMapper.toDto(teacherProfile)).thenReturn(teacherProfileDTO);

        List<TeacherProfileDTO> result = profileService.findAllTeachers();

        assertEquals(1, result.size());
        assertEquals(teacherProfileDTO, result.get(0));
        verify(teacherProfileRepository, times(1)).findAll();
    }

    @Test
    public void testFindTeacherById() {
        Long teacherId = 1L;
        TeacherProfile teacherProfile = new TeacherProfile();
        TeacherProfileDTO teacherProfileDTO = new TeacherProfileDTO();
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.of(teacherProfile));
        when(profileMapper.toDto(teacherProfile)).thenReturn(teacherProfileDTO);

        TeacherProfileDTO result = profileService.findTeacherById(teacherId);

        assertEquals(teacherProfileDTO, result);
        verify(teacherProfileRepository, times(1)).findById(teacherId);
    }

    @Test
    public void testFindTeacherById_NotFound() {
        Long teacherId = 1L;
        when(teacherProfileRepository.findById(teacherId)).thenReturn(Optional.empty());

        TeacherProfileDTO result = profileService.findTeacherById(teacherId);

        assertNull(result);
        verify(teacherProfileRepository, times(1)).findById(teacherId);
    }

    @Test
    public void testCreateTeacher() {
        TeacherProfileDTO teacherProfileDTO = new TeacherProfileDTO();
        teacherProfileDTO.setUserId(1L);
        teacherProfileDTO.setFacultyId(1L);
        User user = new User();
        Faculty faculty = new Faculty();
        TeacherProfile teacherProfile = new TeacherProfile();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(profileMapper.toEntity(teacherProfileDTO, user, faculty)).thenReturn(teacherProfile);
        when(teacherProfileRepository.save(teacherProfile)).thenReturn(teacherProfile);
        when(profileMapper.toDto(teacherProfile)).thenReturn(teacherProfileDTO);

        TeacherProfileDTO result = profileService.createTeacher(teacherProfileDTO);

        assertEquals(teacherProfileDTO, result);
        verify(userRepository, times(1)).findById(1L);
        verify(facultyRepository, times(1)).findById(1L);
        verify(teacherProfileRepository, times(1)).save(teacherProfile);
    }

    @Test
    public void testUpdateTeacher() {
        Long teacherId = 1L;
        TeacherProfileDTO teacherProfileDTO = new TeacherProfileDTO();
        teacherProfileDTO.setUserId(1L);
        teacherProfileDTO.setFacultyId(1L);
        User user = new User();
        Faculty faculty = new Faculty();
        TeacherProfile teacherProfile = new TeacherProfile();
        when(teacherProfileRepository.existsById(teacherId)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(profileMapper.toEntity(teacherProfileDTO, user, faculty)).thenReturn(teacherProfile);
        when(teacherProfileRepository.save(teacherProfile)).thenReturn(teacherProfile);
        when(profileMapper.toDto(teacherProfile)).thenReturn(teacherProfileDTO);

        TeacherProfileDTO result = profileService.updateTeacher(teacherId, teacherProfileDTO);

        assertEquals(teacherProfileDTO, result);
        verify(teacherProfileRepository, times(1)).existsById(teacherId);
        verify(userRepository, times(1)).findById(1L);
        verify(facultyRepository, times(1)).findById(1L);
        verify(teacherProfileRepository, times(1)).save(teacherProfile);
    }

    @Test
    public void testDeleteTeacher() {
        Long teacherId = 1L;
        when(teacherProfileRepository.existsById(teacherId)).thenReturn(true);

        profileService.deleteTeacher(teacherId);

        verify(teacherProfileRepository, times(1)).existsById(teacherId);
        verify(teacherProfileRepository, times(1)).deleteById(teacherId);
    }

    @Test
    public void testFindAllStudents() {
        StudentProfile studentProfile = new StudentProfile();
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();
        when(studentProfileRepository.findAll()).thenReturn(List.of(studentProfile));
        when(profileMapper.toDto(studentProfile)).thenReturn(studentProfileDTO);

        List<StudentProfileDTO> result = profileService.findAllStudents();

        assertEquals(1, result.size());
        assertEquals(studentProfileDTO, result.get(0));
        verify(studentProfileRepository, times(1)).findAll();
    }

    @Test
    public void testFindStudentById() {
        Long studentId = 1L;
        StudentProfile studentProfile = new StudentProfile();
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.of(studentProfile));
        when(profileMapper.toDto(studentProfile)).thenReturn(studentProfileDTO);

        StudentProfileDTO result = profileService.findStudentById(studentId);

        assertEquals(studentProfileDTO, result);
        verify(studentProfileRepository, times(1)).findById(studentId);
    }

    @Test
    public void testFindStudentById_NotFound() {
        Long studentId = 1L;
        when(studentProfileRepository.findById(studentId)).thenReturn(Optional.empty());

        StudentProfileDTO result = profileService.findStudentById(studentId);

        assertNull(result);
        verify(studentProfileRepository, times(1)).findById(studentId);
    }

    @Test
    public void testCreateStudent() {
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();
        studentProfileDTO.setUserId(1L);
        studentProfileDTO.setGroupId(1L);
        User user = new User();
        Group group = new Group();
        StudentProfile studentProfile = new StudentProfile();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(profileMapper.toEntity(studentProfileDTO, user, group)).thenReturn(studentProfile);
        when(studentProfileRepository.save(studentProfile)).thenReturn(studentProfile);
        when(profileMapper.toDto(studentProfile)).thenReturn(studentProfileDTO);

        StudentProfileDTO result = profileService.createStudent(studentProfileDTO);

        assertEquals(studentProfileDTO, result);
        verify(userRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).findById(1L);
        verify(studentProfileRepository, times(1)).save(studentProfile);
    }

    @Test
    public void testUpdateStudent() {
        Long studentId = 1L;
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();
        studentProfileDTO.setUserId(1L);
        studentProfileDTO.setGroupId(1L);
        User user = new User();
        Group group = new Group();
        StudentProfile studentProfile = new StudentProfile();
        when(studentProfileRepository.existsById(studentId)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(profileMapper.toEntity(studentProfileDTO, user, group)).thenReturn(studentProfile);
        when(studentProfileRepository.save(studentProfile)).thenReturn(studentProfile);
        when(profileMapper.toDto(studentProfile)).thenReturn(studentProfileDTO);

        StudentProfileDTO result = profileService.updateStudent(studentId, studentProfileDTO);

        assertEquals(studentProfileDTO, result);
        verify(studentProfileRepository, times(1)).existsById(studentId);
        verify(userRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).findById(1L);
        verify(studentProfileRepository, times(1)).save(studentProfile);
    }

    @Test
    public void testDeleteStudent() {
        Long studentId = 1L;
        when(studentProfileRepository.existsById(studentId)).thenReturn(true);

        profileService.deleteStudent(studentId);

        verify(studentProfileRepository, times(1)).existsById(studentId);
        verify(studentProfileRepository, times(1)).deleteById(studentId);
    }
}
