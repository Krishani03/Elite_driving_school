package com.example.elite_driving_school.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "payments")
    public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private BigDecimal amount;

        private LocalDateTime paymentDate;

        private String method; // cash, card, etc.

        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;

        // @ManyToMany
        // private Set<Course> courses;

        // Getters & Setters
    }


