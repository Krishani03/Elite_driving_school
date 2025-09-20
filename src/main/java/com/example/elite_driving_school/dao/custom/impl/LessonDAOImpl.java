package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.custom.LessonDAO;

import com.example.elite_driving_school.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;

public class LessonDAOImpl implements LessonDAO {
        private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

        @Override
        public boolean save(Lesson lesson) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(lesson);
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
        public boolean update(Lesson lesson) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(lesson);
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
                Lesson lesson = session.get(Lesson.class, id);
                if (lesson != null) {
                    session.remove(lesson);
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
        public ArrayList<Lesson> search(String id) {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Lesson> query = session.createQuery("FROM Lesson WHERE id = :lessonId", Lesson.class);
                query.setParameter("lessonId", id);
                ArrayList<Lesson> list = (ArrayList<Lesson>) query.getResultList();
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
        public ArrayList<Lesson> getAll() {
            Session session = factoryConfiguration.getSession();
            Transaction transaction = session.beginTransaction();
            try {
                Query<Lesson> query = session.createQuery("FROM Lesson", Lesson.class);
                ArrayList<Lesson> list = (ArrayList<Lesson>) query.getResultList();
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
            String nextId = "L1001";
            try {
                Query<String> query = session.createQuery("SELECT id FROM Lesson ORDER BY id DESC", String.class);
                query.setMaxResults(1);
                String lastId = query.uniqueResult();
                if (lastId != null) {
                    int lastNum = Integer.parseInt(lastId.substring(1));
                    return String.format("L%04d", lastNum + 1);
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


