package com.example.elite_driving_school.dto;

import java.io.Serializable;
import com.example.elite_driving_school.entity.User;

public class UserDTO implements Serializable {

        private Long id;  // Match entity type
        private String username;
        private String password; // plain password (to be hashed before saving)
        private User.Role role;
        private boolean active;

        public UserDTO() {

        }

        public UserDTO(Long id, String username, String password, User.Role role, boolean active) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.active = active;
        }

    public UserDTO(Long id, String username, String passwordHash, User.Role role) {
    }

    public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public User.Role getRole() { return role; }
        public void setRole(User.Role role) { this.role = role; }

        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
}
