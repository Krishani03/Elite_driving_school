package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.LessonDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LessonBO extends SuperBO {
    public LessonDTO saveLesson(LessonDTO dto);
    public boolean updateLesson(LessonDTO dto);
    public boolean deleteLesson(String id);
    public LessonDTO searchLesson(String id) throws SQLException;
    public List<LessonDTO> getAllLessons() ;
    }
