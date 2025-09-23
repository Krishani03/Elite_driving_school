package com.example.elite_driving_school.bo.custom;

import com.example.elite_driving_school.bo.SuperBO;
import com.example.elite_driving_school.dto.PaymentDTO;
import com.example.elite_driving_school.entity.Student;
import com.example.elite_driving_school.exception.PaymentException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public void processPayment(PaymentDTO paymentDTO, Student student, Session session) throws PaymentException;
    public List<PaymentDTO> getPaymentsByStudent(Student student, Session session) throws SQLException;
    public String getNextPaymentId(Session session) throws SQLException;
}
