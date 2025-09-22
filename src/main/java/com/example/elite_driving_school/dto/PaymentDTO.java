package com.example.elite_driving_school.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO implements Serializable {
    private long id;
    private BigDecimal amount;
    private LocalDateTime payment_date;
    private String method;

    public PaymentDTO() {
    }
    public PaymentDTO(long id, BigDecimal amount, LocalDateTime payment_date, String method) {
        this.id = id;
        this.amount = amount;
        this.payment_date = payment_date;
        this.method = method;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
