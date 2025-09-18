package com.example.elite_driving_school.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.Table;

@Entity
@Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String username;

        @Column(nullable = false)
        private String passwordHash; // store with BCrypt

        @Enumerated(EnumType.STRING)
        private Role role; // ADMIN, RECEPTIONIST

        private boolean active = true;

        public enum Role {
            ADMIN, RECEPTIONIST
        }

        // Getters & Setters
    }

