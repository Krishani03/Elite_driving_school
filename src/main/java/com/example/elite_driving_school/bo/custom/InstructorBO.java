package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.InstructorDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InstructorBO extends SuperBO {

    boolean saveInstructor(InstructorDTO dto);
    boolean updateInstructor(InstructorDTO dto);
    boolean deleteInstructor(Long id) throws SQLException;
    InstructorDTO searchInstructor(Long id) throws SQLException;
    List<InstructorDTO> getAllInstructors() throws SQLException;
}
