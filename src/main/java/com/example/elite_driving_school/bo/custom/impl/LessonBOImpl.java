package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.LessonBO;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.LessonDAO;
import com.example.elite_driving_school.dto.LessonDTO;
import com.example.elite_driving_school.entity.Course;
import com.example.elite_driving_school.entity.Instructor;
import com.example.elite_driving_school.entity.Lesson;
import com.example.elite_driving_school.entity.Student;
import com.example.elite_driving_school.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonBOImpl implements LessonBO {

    private final LessonDAO lessonDAO = (LessonDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LESSON);

    @Override
    public boolean saveLesson(LessonDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // Map DTO -> Entity
            Lesson lesson = new Lesson();
            lesson.setStartTime(dto.getStartTime());
            lesson.setEndTime(dto.getEndTime());

            // Load related entities
            Student student = session.get(Student.class, dto.getStudentId());
            Course course = session.get(Course.class, dto.getCourseId());
            Instructor instructor = session.get(Instructor.class, dto.getInstructorId());

            if (student == null || course == null || instructor == null) {
                throw new RuntimeException("Invalid Student, Course, or Instructor ID");
            }

            lesson.setStudent(student);
            lesson.setCourse(course);
            lesson.setInstructor(instructor);

            boolean result = lessonDAO.save(lesson, session);
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
    public boolean updateLesson(LessonDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Lesson lesson = session.get(Lesson.class, dto.getId());
            if (lesson == null) {
                throw new RuntimeException("Lesson not found");
            }

            lesson.setStartTime(dto.getStartTime());
            lesson.setEndTime(dto.getEndTime());

            Student student = session.get(Student.class, dto.getStudentId());
            Course course = session.get(Course.class, dto.getCourseId());
            Instructor instructor = session.get(Instructor.class, dto.getInstructorId());

            if (student == null || course == null || instructor == null) {
                throw new RuntimeException("Invalid Student, Course, or Instructor ID");
            }

            lesson.setStudent(student);
            lesson.setCourse(course);
            lesson.setInstructor(instructor);

            boolean result = lessonDAO.update(lesson, session);
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
    public boolean deleteLesson(Long id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean result = lessonDAO.delete(id.toString(), session);
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
    public LessonDTO searchLesson(Long id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Lesson lesson = lessonDAO.search(id.toString(), session);
            if (lesson != null) {
                return new LessonDTO(
                        lesson.getId(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        lesson.getStudent().getId(),
                        lesson.getCourse().getId(),
                        lesson.getInstructor().getId()
                );
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<LessonDTO> getAllLessons() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            ArrayList<Lesson> lessons = lessonDAO.getAll(session);
            ArrayList<LessonDTO> dtoList = new ArrayList<>();
            for (Lesson lesson : lessons) {
                dtoList.add(new LessonDTO(
                        lesson.getId(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        lesson.getStudent().getId(),
                        lesson.getCourse().getId(),
                        lesson.getInstructor().getId()
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
    public String getNextLessonId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return lessonDAO.getNextId(session);
        } finally {
            session.close();
        }
    }
}
