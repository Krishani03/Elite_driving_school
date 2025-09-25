package com.example.elite_driving_school.dto;

import com.example.elite_driving_school.entity.Student;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO implements Serializable {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime payment_date;
    private String method;

    private String studentName;
    private Student student;

    public PaymentDTO() {}

    public PaymentDTO(Long id, BigDecimal amount, LocalDateTime payment_date, String method, Student student) {
        this.id = id;
        this.amount = amount;
        this.payment_date = payment_date;
        this.method = method;
        this.student = student;
        this.studentName = student != null ? student.getFirstName() : null;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getPayment_date() { return payment_date; }
    public void setPayment_date(LocalDateTime payment_date) { this.payment_date = payment_date; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
        this.studentName = student != null ? student.getFirstName() : null;
    }
}
