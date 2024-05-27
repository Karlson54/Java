package com.example.finance.dto.student;

import lombok.Data;

@Data
public class StudentProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long userId;
    private Long groupId;
}
