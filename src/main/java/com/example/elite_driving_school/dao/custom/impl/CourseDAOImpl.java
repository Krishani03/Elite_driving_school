package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.CourseDAO;
import com.example.elite_driving_school.entity.Course;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


    public List<Course> getAll(Session session) {
        Query<Course> query = session.createQuery("FROM Course", Course.class);
        return query.getResultList(); // returns List<Course>
    }

    @Override
    public String getNextId(Session session) throws SQLException {
        Query<String> query = session.createQuery("SELECT c.id FROM Course c ORDER BY c.id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1)); // remove 'P'
            return "C" + (num + 1);
        }
        return "C1001";
    }


}
