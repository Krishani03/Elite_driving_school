package com.example.elite_driving_school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private String id;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private String method; // cash, card, etc.

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // If you want to link courses later:
    // @ManyToMany
    // private Set<Course> courses;
}
