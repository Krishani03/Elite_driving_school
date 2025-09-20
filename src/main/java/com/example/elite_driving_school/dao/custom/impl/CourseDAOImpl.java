package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.custom.CourseDAO;
import com.example.elite_driving_school.entity.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;

public class CourseDAOImpl implements CourseDAO {
        private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

        @Override
        public boolean save(Course course) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(course);
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
        public boolean update(Course course) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(course);
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
                Course course = session.get(Course.class, id);
                if (course != null) {
                    session.remove(course);
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
        public ArrayList<Course> search(String id) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Course> query = session.createQuery("FROM Course WHERE id = :courseId", Course.class);
                query.setParameter("courseId", id);
                ArrayList<Course> list = (ArrayList<Course>) query.getResultList();
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
        public ArrayList<Course> getAll() {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Course> query = session.createQuery("FROM Course", Course.class);
                ArrayList<Course> list = (ArrayList<Course>) query.getResultList();
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
            String nextId = "C1001";
            try {
                Query<String> query = session.createQuery("SELECT id FROM Course ORDER BY id DESC", String.class);
                query.setMaxResults(1);
                String lastId = query.uniqueResult();
                if (lastId != null) {
                    int lastNum = Integer.parseInt(lastId.substring(1));
                    return String.format("C%04d", lastNum + 1);
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

