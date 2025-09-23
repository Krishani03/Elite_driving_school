package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.InstructorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InstructorBO extends SuperBO {
    public boolean saveInstructor(InstructorDTO dto);
    public boolean updateInstructor(InstructorDTO dto);
    public boolean deleteInstructor(String id);
    public InstructorDTO searchInstructor(String id) throws SQLException;
    public ArrayList<InstructorDTO> getAllInstructors();
    public String getNextInstructorId() throws SQLException;
}
