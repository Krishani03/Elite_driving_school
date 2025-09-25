package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.CourseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CourseBO extends SuperBO {
    public boolean saveCourse(CourseDTO dto);
    public boolean updateCourse(CourseDTO dto);
    public boolean deleteCourse(Long id);
    public CourseDTO searchCourse(Long id) throws SQLException;
    List<CourseDTO> getAllCourses();
}
