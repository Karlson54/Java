package com.example.finance.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "control")
@EqualsAndHashCode(callSuper = true)

public class Control extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private ControlType type;

    private Long dueDate;
    private String description;
    private String title;

    public enum ControlType {
        EXAM, HOMEWORK, TEST
    }
}