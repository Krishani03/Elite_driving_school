package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.StudentBO;
import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.StudentDAO;
import com.example.elite_driving_school.dto.StudentDTO;
import com.example.elite_driving_school.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public boolean saveStudent(StudentDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Student student = new Student();
            student.setFirstName(dto.getFirst_name());
            student.setLastName(dto.getLast_name());
            student.setEmail(dto.getEmail());
            student.setPhone(dto.getPhone());
            student.setRegistrationDate(dto.getRegistration_date());

            boolean result = studentDAO.save(student, session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateStudent(StudentDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Student student = session.get(Student.class, Long.valueOf(dto.getId())); // fetch existing
            if (student == null) return false;

            student.setFirstName(dto.getFirst_name());
            student.setLastName(dto.getLast_name());
            student.setEmail(dto.getEmail());
            student.setPhone(dto.getPhone());
            student.setRegistrationDate(dto.getRegistration_date());

            boolean result = studentDAO.update(student, session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteStudent(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean result = studentDAO.delete(id, session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public StudentDTO searchStudent(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Student student = studentDAO.search(id, session);
            if (student != null) {
                return new StudentDTO(
                        String.valueOf(student.getId()),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getRegistrationDate()
                );
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            ArrayList<Student> students = studentDAO.getAll(session);
            ArrayList<StudentDTO> dtoList = new ArrayList<>();
            for (Student student : students) {
                dtoList.add(new StudentDTO(
                        String.valueOf(student.getId()),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getRegistrationDate()
                ));
            }
            return dtoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public String getNextStudentId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return studentDAO.getNextId(session);
        } finally {
            session.close();
        }
    }
}
