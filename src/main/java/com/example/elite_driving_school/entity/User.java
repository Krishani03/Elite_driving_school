package com.example.elite_driving_school.entity;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public User(String username, String passwordHash, Role role, boolean active) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
    }

    public User(Long id, String username, String passwordHash, Role role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = true; // default active
    }



    public enum Role {
        ADMIN, RECEPTIONIST
    }
}


