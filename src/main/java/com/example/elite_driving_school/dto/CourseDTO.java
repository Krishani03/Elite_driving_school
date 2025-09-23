package com.example.elite_driving_school.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CourseDTO implements Serializable {
    private Long course_id;
    private String name;
    private String duration;
    private BigDecimal fee;

    public CourseDTO() {
    }
    public CourseDTO(Long course_id, String name, String duration, BigDecimal fee) {
        this.course_id = course_id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
