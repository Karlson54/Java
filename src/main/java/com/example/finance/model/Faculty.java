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
@Table(name = "faculty")
@EqualsAndHashCode(callSuper = true)

public class Faculty extends BaseEntity {
    private String name;
}
