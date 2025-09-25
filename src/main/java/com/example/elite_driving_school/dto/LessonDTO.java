package com.example.elite_driving_school.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LessonDTO implements Serializable {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String studentId;
    private String courseId;
    private String instructorId;

    private String studentName;
    private String courseName;
    private String instructorName;

    public LessonDTO() {
    }

    public LessonDTO(String id, LocalDateTime startTime, LocalDateTime endTime,
                     String studentId, String courseId, String instructorId,
                     String studentName, String courseName, String instructorName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.instructorName = instructorName;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
