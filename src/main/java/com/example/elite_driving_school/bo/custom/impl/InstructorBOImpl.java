package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.InstructorBO;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.InstructorDAO;
import com.example.elite_driving_school.dto.InstructorDTO;
import com.example.elite_driving_school.entity.Instructor;
import com.example.elite_driving_school.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class InstructorBOImpl implements InstructorBO {

    private final InstructorDAO instructorDAO =
            (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);

    @Override
    public boolean saveInstructor(InstructorDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = Instructor.builder()
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .build();

            boolean result = instructorDAO.save(instructor, session);
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
    public boolean updateInstructor(InstructorDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = Instructor.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .build();

            boolean result = instructorDAO.update(instructor, session);
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
    public boolean deleteInstructor(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean result = instructorDAO.delete(id, session);
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
    public InstructorDTO searchInstructor(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Instructor instructor = instructorDAO.search(id, session);
            if (instructor != null) {
                return new InstructorDTO(
                        instructor.getId(),
                        instructor.getName(),
                        instructor.getPhone(),
                        instructor.getEmail()
                );
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<InstructorDTO> getAllInstructors() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            ArrayList<Instructor> instructors = instructorDAO.getAll(session);
            ArrayList<InstructorDTO> dtoList = new ArrayList<>();
            for (Instructor instructor : instructors) {
                dtoList.add(new InstructorDTO(
                        instructor.getId(),
                        instructor.getName(),
                        instructor.getPhone(),
                        instructor.getEmail()
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
    public String getNextInstructorId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return instructorDAO.getNextId(session);
        } finally {
            session.close();
        }
    }
}

