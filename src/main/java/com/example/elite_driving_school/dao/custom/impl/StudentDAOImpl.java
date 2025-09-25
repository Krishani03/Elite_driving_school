package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.dao.custom.StudentDAO;
import com.example.elite_driving_school.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student student, Session session) {
        session.persist(student); // Hibernate auto-generates ID
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
    public List<Student> getAll(Session session) {
        Query<Student> query = session.createQuery("FROM Student", Student.class);
        return query.getResultList();
    }

    @Override
    public String getNextId(Session session) {

            String lastId = (String) session.createQuery("SELECT s.id FROM Student s ORDER BY s.id DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int num = Integer.parseInt(lastId.substring(1)); // remove 'S'
                num++;
                return String.format("S%03d", num);
            } else {
                return "S001";
            }
        }

    }


