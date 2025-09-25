package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.InstructorDAO;
import com.example.elite_driving_school.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

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
    public boolean delete(Long id, Session session) {
        Instructor instructor = session.get(Instructor.class, id);
        if (instructor != null) {
            session.remove(instructor);
            return true;
        }
        return false;
    }

    @Override
    public Instructor search(Long id, Session session) {
        return session.get(Instructor.class, id);
    }

    @Override
    public List<Instructor> getAll(Session session) {
        Query<Instructor> query = session.createQuery("FROM Instructor", Instructor.class);
        return query.getResultList(); // returns List<Instructor>
    }


}
