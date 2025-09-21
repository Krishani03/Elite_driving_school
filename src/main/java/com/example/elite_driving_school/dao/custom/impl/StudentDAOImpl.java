package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.custom.StudentDAO;
import com.example.elite_driving_school.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student student, Session session) {
        session.persist(student);
        return true;
    }

    @Override
    public boolean update(Student student, Session session) {
        session.merge(student);
        return true;
    }

    @Override
    public boolean delete(String id, Session session) {
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.remove(student);
            return true;
        }
        return false;
    }

    @Override
    public Student search(String id, Session session) {
        return session.get(Student.class, id);
    }

    @Override
    public ArrayList<Student> getAll(Session session) {
        Query<Student> query = session.createQuery("FROM Student", Student.class);
        return (ArrayList<Student>) query.getResultList();
    }

    @Override
    public String getNextId(Session session) {
        Query<String> query = session.createQuery("SELECT id FROM Student ORDER BY id DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        if (lastId != null) {
            int lastNum = Integer.parseInt(lastId.substring(1));
            return String.format("S%04d", lastNum + 1);
        }
        return "S1001";
    }
}


