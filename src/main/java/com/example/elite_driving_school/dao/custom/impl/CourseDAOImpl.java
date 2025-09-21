package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.CourseDAO;
import com.example.elite_driving_school.entity.Course;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public boolean save(Course course, Session session) {
        session.persist(course);
        return true;
    }

    @Override
    public boolean update(Course course, Session session) {
        session.merge(course);
        return true;
    }

    @Override
    public boolean delete(String id, Session session) {
        Course course = session.get(Course.class, id);
        if (course != null) {
            session.remove(course);
            return true;
        }
        return false;
    }

    @Override
    public Course search(String id, Session session) {
        return session.get(Course.class, id);
    }

    @Override
    public ArrayList<Course> getAll(Session session) {
        Query<Course> query = session.createQuery("FROM Course", Course.class);
        return (ArrayList<Course>) query.getResultList();
    }

    @Override
    public String getNextId(Session session) {
        Query<String> query = session.createQuery("SELECT id FROM Course ORDER BY id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int lastNum = Integer.parseInt(lastId.substring(1));
            return String.format("C%04d", lastNum + 1);
        }
        return "C1001";
    }
}
