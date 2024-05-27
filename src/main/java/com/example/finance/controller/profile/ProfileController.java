package com.example.finance.controller.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.finance.dto.student.StudentProfileDTO;
import com.example.finance.dto.teacher.TeacherProfileDTO;
import com.example.finance.service.profile.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/teachers")
    public List<TeacherProfileDTO> getAllTeachers() {
        return profileService.findAllTeachers();
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherProfileDTO> getTeacherById(@PathVariable Long id) {
        TeacherProfileDTO teacherProfileDTO = profileService.findTeacherById(id);
        if (teacherProfileDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacherProfileDTO);
    }

    @PostMapping("/teachers")
    public ResponseEntity<TeacherProfileDTO> createTeacher(@RequestBody TeacherProfileDTO teacherProfileDTO) {
        TeacherProfileDTO createdTeacher = profileService.createTeacher(teacherProfileDTO);
        return ResponseEntity.ok(createdTeacher);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<TeacherProfileDTO> updateTeacher(@PathVariable Long id, @RequestBody TeacherProfileDTO teacherProfileDTO) {
        TeacherProfileDTO updatedTeacher = profileService.updateTeacher(id, teacherProfileDTO);
        if (updatedTeacher == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        profileService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students")
    public List<StudentProfileDTO> getAllStudents() {
        return profileService.findAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentProfileDTO> getStudentById(@PathVariable Long id) {
        StudentProfileDTO studentProfileDTO = profileService.findStudentById(id);
        if (studentProfileDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentProfileDTO);
    }

    @PostMapping("/students")
    public ResponseEntity<StudentProfileDTO> createStudent(@RequestBody StudentProfileDTO studentProfileDTO) {
        StudentProfileDTO createdStudent = profileService.createStudent(studentProfileDTO);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentProfileDTO> updateStudent(@PathVariable Long id, @RequestBody StudentProfileDTO studentProfileDTO) {
        StudentProfileDTO updatedStudent = profileService.updateStudent(id, studentProfileDTO);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        profileService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
