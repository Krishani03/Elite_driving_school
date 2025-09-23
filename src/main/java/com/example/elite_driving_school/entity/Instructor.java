package com.example.elite_driving_school.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class Instructor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String phone;
        private String email;

        @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Lesson> lessons = new HashSet<>();

        // Getters & Setters
    }


