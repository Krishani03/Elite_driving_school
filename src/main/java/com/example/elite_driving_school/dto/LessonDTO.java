package com.example.elite_driving_school.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LessonDTO implements Serializable {
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long studentId;
    private Long courseId;
    private Long instructorId;

    private String studentName;
    private String courseName;
    private String instructorName;

    public LessonDTO() {
    }

    public LessonDTO(Long id, LocalDateTime startTime, LocalDateTime endTime,
                     Long studentId, Long courseId, Long instructorId,
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
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
