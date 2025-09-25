package com.example.elite_driving_school.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.math.BigDecimal;
import java.util.HashSet;



@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class Course {
        @Id
        @Column(length = 10)
        private String id; // e.g., "C1001"

        private String name;

        private String duration;

        private BigDecimal fee;

        @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
        private Set<Student> students = new HashSet<>();

        @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Lesson> lessons = new HashSet<>();


 }


