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
import java.util.List;

public class InstructorBOImpl implements InstructorBO {

    private final InstructorDAO instructorDAO =
            (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);


    @Override
    public boolean saveInstructor(InstructorDTO dto) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            Instructor instructor = Instructor.builder()
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .build();

            boolean result = instructorDAO.save(instructor, session);

            transaction.commit();

            dto.setId(instructor.getId());

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateInstructor(InstructorDTO dto) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

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
        }
    }

    @Override
    public boolean deleteInstructor(Long id) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            boolean result = instructorDAO.delete(id, session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public InstructorDTO searchInstructor(Long id) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
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
        }
    }

    @Override
    public List<InstructorDTO> getAllInstructors() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            List<Instructor> instructors = instructorDAO.getAll(session);
            List<InstructorDTO> dtoList = new ArrayList<>();
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
        }
    }
}
