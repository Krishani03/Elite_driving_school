package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.LessonDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LessonBO extends SuperBO {
    public boolean saveLesson(LessonDTO dto);
    public boolean updateLesson(LessonDTO dto);
    public boolean deleteLesson(Long id);
    public LessonDTO searchLesson(Long id) throws SQLException;
    public ArrayList<LessonDTO> getAllLessons() ;
    public String getNextLessonId() throws SQLException;
    }
