package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.LessonDAO;
import com.example.elite_driving_school.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class LessonDAOImpl implements LessonDAO {

    @Override
    public boolean save(Lesson lesson, Session session) {
        session.persist(lesson);
        return true;
    }

    @Override
    public boolean update(Lesson lesson, Session session) {
        session.merge(lesson);
        return true;
    }

    @Override
    public boolean delete(String id, Session session) {
        Lesson lesson = session.get(Lesson.class, id);
        if (lesson != null) {
            session.remove(lesson);
            return true;
        }
        return false;
    }

    @Override
    public Lesson search(String id, Session session) {
        return session.get(Lesson.class, id);
    }

    @Override
    public ArrayList<Lesson> getAll(Session session) {
        Query<Lesson> query = session.createQuery("FROM Lesson", Lesson.class);
        return (ArrayList<Lesson>) query.getResultList();
    }

    @Override
    public String getNextId(Session session) throws SQLException {
        Query<String> query = session.createQuery(
                "SELECT id FROM Lesson ORDER BY id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("L%04d", num);
        }
        return "L1001";
    }


}
