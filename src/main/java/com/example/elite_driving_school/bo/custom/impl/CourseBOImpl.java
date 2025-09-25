package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.CourseBO;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.CourseDAO;
import com.example.elite_driving_school.dto.CourseDTO;
import com.example.elite_driving_school.entity.Course;
import com.example.elite_driving_school.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {

    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance()
            .getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public boolean saveCourse(CourseDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Course course = Course.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .duration(dto.getDuration())
                    .fee(dto.getFee())
                    .build();

            boolean result = courseDAO.save(course, session);
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
    public boolean updateCourse(CourseDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Course course = Course.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .duration(dto.getDuration())
                    .fee(dto.getFee())
                    .build();

            boolean result = courseDAO.update(course, session);
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
    public boolean deleteCourse(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean result = courseDAO.delete(id, session);
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
    public CourseDTO searchCourse(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Course course = courseDAO.search(id, session);
            if (course != null) {
                return new CourseDTO(
                        course.getId(),
                        course.getName(),
                        course.getDuration(),
                        course.getFee()
                );
            }
            return null;
        } finally {
            session.close();
        }
    }


    @Override
    public List<CourseDTO> getAllCourses() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Course> courses = courseDAO.getAll(session);
            List<CourseDTO> dtoList = new ArrayList<>();
            for (Course course : courses) {
                dtoList.add(new CourseDTO(
                        course.getId(),
                        course.getName(),
                        course.getDuration(),
                        course.getFee()
                ));
            }
            return dtoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }



}