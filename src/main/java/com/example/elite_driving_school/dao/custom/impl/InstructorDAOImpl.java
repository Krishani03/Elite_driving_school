package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.InstructorDAO;
import com.example.elite_driving_school.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class InstructorDAOImpl implements InstructorDAO {

    @Override
    public boolean save(Instructor instructor, Session session) {
        session.persist(instructor);
        return true;
    }

    @Override
    public boolean update(Instructor instructor, Session session) {
        session.merge(instructor);
        return true;
    }

    @Override
    public boolean delete(String id, Session session) {
        Instructor instructor = session.get(Instructor.class, id);
        if (instructor != null) {
            session.remove(instructor);
            return true;
        }
        return false;
    }

    @Override
    public Instructor search(String id, Session session) {
        return session.get(Instructor.class, id);
    }

    @Override
    public ArrayList<Instructor> getAll(Session session) {
        Query<Instructor> query = session.createQuery("FROM Instructor", Instructor.class);
        return (ArrayList<Instructor>) query.getResultList();
    }

    @Override
    public String getNextId(Session session) {
        Query<String> query = session.createQuery("SELECT id FROM Instructor ORDER BY id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int lastNum = Integer.parseInt(lastId.substring(1));
            return String.format("I%04d", lastNum + 1);
        }
        return "I1001";
    }
}
