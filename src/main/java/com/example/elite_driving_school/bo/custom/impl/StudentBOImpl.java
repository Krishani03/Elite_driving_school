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
import java.util.List;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public StudentDTO saveStudent(StudentDTO dto) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            String nextId = studentDAO.getNextId(session);

            Student student = new Student();
            student.setId(nextId);
            student.setFirstName(dto.getFirst_name());
            student.setLastName(dto.getLast_name());
            student.setEmail(dto.getEmail());
            student.setPhone(dto.getPhone());
            student.setRegistrationDate(dto.getRegistration_date());

            studentDAO.save(student, session);
            transaction.commit();

            dto.setId(student.getId());
            return dto;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean updateStudent(StudentDTO dto) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, dto.getId());
            if (student == null) return false;

            student.setFirstName(dto.getFirst_name());
            student.setLastName(dto.getLast_name());
            student.setEmail(dto.getEmail());
            student.setPhone(dto.getPhone());
            student.setRegistrationDate(dto.getRegistration_date());

            studentDAO.update(student, session);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudent(String id) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            boolean deleted = studentDAO.delete(id, session);
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public StudentDTO searchStudent(String id) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Student student = studentDAO.search(id, session);
            if (student != null) {
                return new StudentDTO(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getRegistrationDate()
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            List<Student> students = studentDAO.getAll(session);
            ArrayList<StudentDTO> dtoList = new ArrayList<>();
            for (Student student : students) {
                dtoList.add(new StudentDTO(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getRegistrationDate()
                ));
            }
            return dtoList;
        }
    }


}
