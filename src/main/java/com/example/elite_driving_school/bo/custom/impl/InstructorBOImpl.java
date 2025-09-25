package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.InstructorBO;
import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.InstructorDAO;
import com.example.elite_driving_school.dto.InstructorDTO;
import com.example.elite_driving_school.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorBOImpl implements InstructorBO {

    private final InstructorDAO instructorDAO =
            (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);

    @Override
    public InstructorDTO saveInstructor(InstructorDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            String nextId = instructorDAO.getNextId(session);

            Instructor instructor = Instructor.builder()
                    .id(nextId)
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .build();

            instructorDAO.save(instructor, session);
            transaction.commit();

            dto.setId(nextId);
            return dto;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateInstructor(InstructorDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = session.get(Instructor.class, dto.getId());
            if (instructor == null) return false;

            instructor.setName(dto.getName());
            instructor.setPhone(dto.getPhone());
            instructor.setEmail(dto.getEmail());

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
    public InstructorDTO searchInstructor(String id) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<InstructorDTO> getAllInstructors() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Instructor> instructors = instructorDAO.getAll(session);
            List<InstructorDTO> dtoList = new ArrayList<>();
            for (Instructor ins : instructors) {
                dtoList.add(new InstructorDTO(
                        ins.getId(),
                        ins.getName(),
                        ins.getPhone(),
                        ins.getEmail()
                ));
            }
            return dtoList;
        } finally {
            session.close();
        }
    }
    @Override
    public String getNextInstructorId() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return instructorDAO.getNextId(session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
