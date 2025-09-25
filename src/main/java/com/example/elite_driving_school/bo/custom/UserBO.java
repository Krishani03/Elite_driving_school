package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public UserDTO saveUser(UserDTO dto);
    public boolean updateUser(UserDTO dto);
    public boolean deleteUser(String id);
    public UserDTO searchUser(String id) throws SQLException;
    public ArrayList<UserDTO> getAllUsers() throws SQLException;
    public String getNextUserId() throws SQLException;

    UserDTO getUserByUsername(String username) throws SQLException;

}
