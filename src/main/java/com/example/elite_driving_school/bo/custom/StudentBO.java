package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    public boolean saveStudent(StudentDTO dto);
    public boolean updateStudent(StudentDTO dto);
    public boolean deleteStudent(String id);
    public StudentDTO searchStudent(String id) throws SQLException;
    public ArrayList<StudentDTO> getAllStudents();
    public String getNextStudentId() throws SQLException;
}
