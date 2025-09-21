package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.PaymentDAO;
import com.example.elite_driving_school.entity.Payment;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean save(Payment payment, Session session) {
        session.persist(payment);
        return true;
    }

    @Override
    public boolean update(Payment payment, Session session) {
        session.merge(payment);
        return true;
    }

    @Override
    public boolean delete(String id, Session session) {
        Payment payment = session.get(Payment.class, id);
        if (payment != null) {
            session.remove(payment);
            return true;
        }
        return false;
    }

    @Override
    public Payment search(String id, Session session) {
        return session.get(Payment.class, id);
    }

    @Override
    public ArrayList<Payment> getAll(Session session) {
        Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
        return (ArrayList<Payment>) query.getResultList();
    }

    @Override
    public String getNextId(Session session) {
        Query<String> query = session.createQuery("SELECT id FROM Payment ORDER BY id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int lastNum = Integer.parseInt(lastId.substring(1));
            return String.format("P%04d", lastNum + 1);
        }
        return "P1001";
    }
}
