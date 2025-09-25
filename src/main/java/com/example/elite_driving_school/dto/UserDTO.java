package com.example.elite_driving_school.dto;

import java.io.Serializable;
import com.example.elite_driving_school.entity.User;

public class UserDTO implements Serializable {

        private String id;  // Match entity type
        private String username;
        private String password;
        private String role;
        private boolean active;

        public UserDTO() {

        }

        public UserDTO(String id, String username, String password, String role, boolean active) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.active = active;
        }

    public UserDTO(String id, String username, String password, User.Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role.name(); // store enum as string
        this.active = true; // or pass from parameter if needed
    }

    public String getId() {
            return id; }
        public void setId(String id) {
            this.id = id; }

        public String getUsername() {
            return username; }
        public void setUsername(String username) {
            this.username = username; }

        public String getPassword() {
            return password; }
        public void setPassword(String password) {
            this.password = password; }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
}
