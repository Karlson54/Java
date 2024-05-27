package com.example.finance.dto.control;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ControlDTO {
    private Long id;
    private Long disciplineId;
    private Long groupId;
    private String type;
    private LocalDate dueDate;
    private String description;
    private String title;
}
