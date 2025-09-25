package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.InstructorDAO;
import com.example.elite_driving_school.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
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
    public List<Instructor> getAll(Session session) {
        Query<Instructor> query = session.createQuery("FROM Instructor", Instructor.class);
        return query.getResultList(); // returns List<Instructor>
    }

    @Override
    public String getNextId(Session session) throws SQLException {
        Query<String> query = session.createQuery(
                "SELECT i.id FROM Instructor i ORDER BY i.id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        if (lastId != null) {
            int num = Integer.parseInt(lastId.substring(1)); // remove 'I'
            return "I" + (num + 1);
        }

        return "I1001"; // first ID
    }
}
