package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.PaymentDAO;
import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

        private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

        @Override
        public boolean save(Payment payment) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(payment);
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
        }

        @Override
        public boolean update(Payment payment) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(payment);
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
        }

        @Override
        public boolean delete(String id) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Payment payment = session.get(Payment.class, id);
                if (payment != null) {
                    session.remove(payment);
                    transaction.commit();
                    return true;
                }
                return false;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
        }

        @Override
        public ArrayList<Payment> search(String id) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Payment> query = session.createQuery("FROM Payment WHERE id = :paymentId", Payment.class);
                query.setParameter("paymentId", id);
                ArrayList<Payment> list = (ArrayList<Payment>) query.getResultList();
                transaction.commit();
                return list;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return null;
            } finally {
                session.close();
            }
        }

        @Override
        public ArrayList<Payment> getAll() {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
                ArrayList<Payment> list = (ArrayList<Payment>) query.getResultList();
                transaction.commit();
                return list;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return null;
            } finally {
                session.close();
            }
        }

        @Override
        public String getNextId() {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            String nextId = "P1001";
            try {
                Query<String> query = session.createQuery("SELECT id FROM Payment ORDER BY id DESC", String.class);
                query.setMaxResults(1);
                String lastId = query.uniqueResult();
                if (lastId != null) {
                    int lastNum = Integer.parseInt(lastId.substring(1));
                    return String.format("P%04d", lastNum + 1);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            return nextId;
        }
    }


