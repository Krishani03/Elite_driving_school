package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.PaymentBO;
import com.example.elite_driving_school.dao.custom.PaymentDAO;
import com.example.elite_driving_school.dao.custom.impl.PaymentDAOImpl;
import com.example.elite_driving_school.dto.PaymentDTO;
import com.example.elite_driving_school.entity.Payment;
import com.example.elite_driving_school.entity.Student;
import com.example.elite_driving_school.exception.PaymentException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class PaymentBOImpl implements PaymentBO {

    private PaymentDAO paymentDAO = new PaymentDAOImpl();


    @Override
    public void processPayment(PaymentDTO paymentDTO, Student student, Session session) throws PaymentException {
        if (paymentDTO.getAmount() == null || paymentDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Payment amount must be greater than zero.");
        }
        if (paymentDTO.getPayment_date() == null) {
            paymentDTO.setPayment_date(LocalDateTime.now());
        }
        if (paymentDTO.getMethod() == null || paymentDTO.getMethod().isEmpty()) {
            throw new PaymentException("Payment method is required.");
        }

        try {
            Payment payment = new Payment();

            payment.setId(getNextPaymentId(session));
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentDate(paymentDTO.getPayment_date());
            payment.setMethod(paymentDTO.getMethod());
            payment.setStudent(student);

            paymentDAO.save(payment, session);
            paymentDTO.setId(payment.getId());
        } catch (Exception e) {
            log.error("Error processing payment: {}", e.getMessage());
            throw new PaymentException("Failed to process payment: " + e.getMessage());
        }
    }


    @Override
    public List<PaymentDTO> getPaymentsByStudent(Student student, Session session) throws SQLException {
        List<Payment> payments = paymentDAO.getAll(session);

        List<Payment> studentPayments = payments.stream()
                .filter(p -> p.getStudent() != null && p.getStudent().getId().equals(student.getId()))
                .collect(Collectors.toList());

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment p : studentPayments) {
            paymentDTOs.add(new PaymentDTO(
                    p.getId(),
                    p.getAmount(),
                    p.getPaymentDate(),
                    p.getMethod(),
                    p.getStudent()
            ));
        }
        return paymentDTOs;
    }


    @Override
    public String getNextPaymentId(Session session) throws SQLException {
        return paymentDAO.getNextId(session);
    }
}
