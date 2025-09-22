package com.example.elite_driving_school.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LessonDTO implements Serializable {
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String studentId;
    private String courseId;
    private String instructorId;

    public LessonDTO() {
    }
    public LessonDTO(Long id, LocalDateTime startTime, LocalDateTime endTime,
                     String studentId, String courseId, String instructorId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.courseId = courseId;
        this.instructorId = instructorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}
