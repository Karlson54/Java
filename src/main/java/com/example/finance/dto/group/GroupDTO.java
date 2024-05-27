package com.example.finance.dto.group;

import lombok.Data;

@Data
public class GroupDTO {
    private Long id;
    private String title;
    private String courseInfo;
    private Integer semester;
    private Long facultyId;
}
