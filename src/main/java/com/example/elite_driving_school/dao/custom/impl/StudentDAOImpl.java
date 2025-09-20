package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.custom.StudentDAO;
import com.example.elite_driving_school.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {

        private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

        @Override
        public boolean save(Student student) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(student);
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
        public boolean update(Student student) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(student);
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
                Student student = session.get(Student.class, id);
                if (student != null) {
                    session.remove(student);
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
        public ArrayList<Student> search(String id) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Student> query = session.createQuery("FROM Student WHERE id = :studentId", Student.class);
                query.setParameter("studentId", id);
                ArrayList<Student> list = (ArrayList<Student>) query.getResultList();
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
        public ArrayList<Student> getAll() {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Student> query = session.createQuery("FROM Student", Student.class);
                ArrayList<Student> list = (ArrayList<Student>) query.getResultList();
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
            String nextId = "S1001";
            try {
                Query<String> query = session.createQuery("SELECT id FROM Student ORDER BY id DESC", String.class);
                query.setMaxResults(1);
                String lastId = query.uniqueResult();
                if (lastId != null) {
                    int lastNum = Integer.parseInt(lastId.substring(1));
                    return String.format("S%04d", lastNum + 1);
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


