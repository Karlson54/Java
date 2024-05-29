package com.example.finance.dto.control;

import lombok.Data;

@Data
public class ControlDTO {
    private Long id;
    private Long disciplineId;
    private Long groupId;
    private String type;
    private Long dueDate;
    private String description;
    private String title;
}
