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
        try {
            Long longId = Long.parseLong(id);
            Payment payment = session.get(Payment.class, longId);
            if (payment != null) {
                session.remove(payment);
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Payment search(String id, Session session) {
        try {
            Long longId = Long.parseLong(id);
            return session.get(Payment.class, longId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public ArrayList<Payment> getAll(Session session) {
        Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
        return (ArrayList<Payment>) query.getResultList();
    }

    @Override
    public String getNextId(Session session) {
        Query<String> query = session.createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1)); // remove 'P'
            return "P" + (num + 1);
        }
        return "P1001";
    }

}
