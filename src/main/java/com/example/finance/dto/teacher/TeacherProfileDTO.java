package com.example.finance.dto.teacher;

import lombok.Data;

@Data
public class TeacherProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long userId;
    private Long facultyId;
}
